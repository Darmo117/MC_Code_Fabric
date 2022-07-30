package net.darmo_creations.mccode.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.darmo_creations.mccode.MCCode;
import net.darmo_creations.mccode.commands.argument_types.EnumArgumentType;
import net.darmo_creations.mccode.commands.argument_types.ProgramElementNameArgumentType;
import net.darmo_creations.mccode.commands.argument_types.ProgramNameArgumentType;
import net.darmo_creations.mccode.commands.argument_types.ProgramVariableNameArgumentType;
import net.darmo_creations.mccode.interpreter.*;
import net.darmo_creations.mccode.interpreter.exceptions.EvaluationException;
import net.darmo_creations.mccode.interpreter.exceptions.ProgramStatusException;
import net.darmo_creations.mccode.interpreter.exceptions.SyntaxErrorException;
import net.darmo_creations.mccode.interpreter.nodes.Node;
import net.darmo_creations.mccode.interpreter.parser.ExpressionParser;
import net.darmo_creations.mccode.interpreter.type_wrappers.TypeBase;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Command to interact with programs.
 */
public class ProgramCommand {
  public static final String PROGRAM_NAME_ARG = "program_name";
  public static final String PROGRAM_ALIAS_ARG = "alias";
  public static final String PROGRAM_ARGUMENTS_ARG = "arguments";
  public static final String VARIABLE_NAME_ARG = "variable_name";
  public static final String VARIABLE_VALUE_ARG = "value";
  public static final String DOC_TYPE_ARG = "type";
  public static final String ELEMENT_NAME_ARG = "name";

  /**
   * Register this command in the given dispatcher.
   */
  public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
    LiteralArgumentBuilder<ServerCommandSource> loadAndRunProgramOption = CommandManager.literal("run")
        .then(buildLoadProgramBranch(CommandManager.argument(PROGRAM_NAME_ARG, ProgramNameArgumentType.available()), false))
        .then(CommandManager.argument(PROGRAM_NAME_ARG, ProgramNameArgumentType.available())
            .then(CommandManager.literal("as")
                .then(buildLoadProgramBranch(CommandManager.argument(PROGRAM_ALIAS_ARG, StringArgumentType.word()), true))));

    LiteralArgumentBuilder<ServerCommandSource> stopProgramOption = CommandManager.literal("stop")
        .then(CommandManager.argument(PROGRAM_NAME_ARG, ProgramNameArgumentType.loaded())
            .executes(ProgramCommand::stopProgram));

    LiteralArgumentBuilder<ServerCommandSource> resetProgramOption = CommandManager.literal("reset")
        .then(CommandManager.argument(PROGRAM_NAME_ARG, ProgramNameArgumentType.loaded())
            .executes(ProgramCommand::resetProgram));

    LiteralArgumentBuilder<ServerCommandSource> pauseProgramOption = CommandManager.literal("pause")
        .then(CommandManager.argument(PROGRAM_NAME_ARG, ProgramNameArgumentType.loaded())
            .executes(ProgramCommand::pauseProgram));

    LiteralArgumentBuilder<ServerCommandSource> getVariableOption = CommandManager.literal("get")
        .then(CommandManager.argument(PROGRAM_NAME_ARG, ProgramNameArgumentType.loaded())
            .then(CommandManager.argument(VARIABLE_NAME_ARG, ProgramVariableNameArgumentType.variableName())
                .executes(ProgramCommand::getVariableValue)));

    LiteralArgumentBuilder<ServerCommandSource> setVariableOption = CommandManager.literal("set")
        .then(CommandManager.argument(PROGRAM_NAME_ARG, ProgramNameArgumentType.loaded())
            .then(CommandManager.argument(VARIABLE_NAME_ARG, ProgramVariableNameArgumentType.editableVariableName())
                .then(CommandManager.argument(VARIABLE_VALUE_ARG, StringArgumentType.greedyString())
                    .executes(ProgramCommand::setVariableValue))));

    LiteralArgumentBuilder<ServerCommandSource> deleteVariableOption = CommandManager.literal("delete")
        .then(CommandManager.argument(PROGRAM_NAME_ARG, ProgramNameArgumentType.loaded())
            .then(CommandManager.argument(VARIABLE_NAME_ARG, ProgramVariableNameArgumentType.deletableVariableName())
                .executes(ProgramCommand::deleteVariable)));

    LiteralArgumentBuilder<ServerCommandSource> listProgramsOption = CommandManager.literal("list")
        .executes(ProgramCommand::listPrograms);

    LiteralArgumentBuilder<ServerCommandSource> docOption = CommandManager.literal("doc")
        .then(CommandManager.argument(DOC_TYPE_ARG, EnumArgumentType.of(DocType.class))
            .then(CommandManager.argument(ELEMENT_NAME_ARG, ProgramElementNameArgumentType.create())
                .executes(ProgramCommand::showDoc)));

    dispatcher.register(
        CommandManager.literal("program")
            .requires(commandSourceStack -> commandSourceStack.hasPermissionLevel(2))
            .then(loadAndRunProgramOption)
            .then(stopProgramOption)
            .then(resetProgramOption)
            .then(pauseProgramOption)
            .then(getVariableOption)
            .then(setVariableOption)
            .then(deleteVariableOption)
            .then(listProgramsOption)
            .then(docOption)
    );
  }

  private static ArgumentBuilder<ServerCommandSource, ?> buildLoadProgramBranch(
      ArgumentBuilder<ServerCommandSource, ?> root, final boolean hasAlias) {
    return root.executes(context -> loadAndRunProgram(context, hasAlias, false))
        .then(CommandManager.argument(PROGRAM_ARGUMENTS_ARG, StringArgumentType.greedyString())
            .executes(context -> loadAndRunProgram(context, hasAlias, true)));
  }

  private static int loadAndRunProgram(CommandContext<ServerCommandSource> context, final boolean hasAlias, final boolean hasArgs) {
    ProgramManager pm = MCCode.INSTANCE.PROGRAM_MANAGERS.get(context.getSource().getWorld());
    String programName = ProgramNameArgumentType.getName(context, PROGRAM_NAME_ARG);
    String alias = hasAlias ? StringArgumentType.getString(context, PROGRAM_ALIAS_ARG) : null;
    String[] args = hasArgs ? StringArgumentType.getString(context, PROGRAM_ARGUMENTS_ARG).split(" ") : new String[0];
    Program p;
    try {
      p = pm.loadProgram(programName, alias, false, args);
    } catch (SyntaxErrorException e) {
      context.getSource().sendError(new LiteralText("[%s:%d:%d] ".formatted(programName, e.getLine(), e.getColumn()))
          .append(new TranslatableText(e.getTranslationKey(), e.getArgs())));
      return 0;
    } catch (ProgramStatusException e) {
      context.getSource().sendError(new TranslatableText(e.getTranslationKey(), e.getProgramName()));
      return 0;
    }
    try {
      pm.runProgram(p.getName());
    } catch (ProgramStatusException e) {
      context.getSource().sendError(new TranslatableText(e.getTranslationKey(), e.getProgramName()));
      return 0;
    }
    context.getSource().sendFeedback(
        new TranslatableText("commands.program.feedback.program_launched", programName), true);
    return 1;
  }

  private static int stopProgram(CommandContext<ServerCommandSource> context) {
    ProgramManager pm = MCCode.INSTANCE.PROGRAM_MANAGERS.get(context.getSource().getWorld());
    String programName = ProgramNameArgumentType.getName(context, PROGRAM_NAME_ARG);
    try {
      pm.unloadProgram(programName);
    } catch (ProgramStatusException e) {
      context.getSource().sendError(new TranslatableText(e.getTranslationKey(), e.getProgramName()));
      return 0;
    }
    context.getSource().sendFeedback(
        new TranslatableText("commands.program.feedback.program_stopped", programName), true);
    return 1;
  }

  private static int resetProgram(CommandContext<ServerCommandSource> context) {
    ProgramManager pm = MCCode.INSTANCE.PROGRAM_MANAGERS.get(context.getSource().getWorld());
    String programName = ProgramNameArgumentType.getName(context, PROGRAM_NAME_ARG);
    try {
      pm.resetProgram(programName);
    } catch (ProgramStatusException e) {
      context.getSource().sendError(new TranslatableText(e.getTranslationKey(), e.getProgramName()));
      return 0;
    }
    context.getSource().sendFeedback(
        new TranslatableText("commands.program.feedback.program_reset", programName), true);
    return 1;
  }

  private static int pauseProgram(CommandContext<ServerCommandSource> context) {
    ProgramManager pm = MCCode.INSTANCE.PROGRAM_MANAGERS.get(context.getSource().getWorld());
    String programName = ProgramNameArgumentType.getName(context, PROGRAM_NAME_ARG);
    try {
      pm.pauseProgram(programName);
    } catch (ProgramStatusException e) {
      context.getSource().sendError(new TranslatableText(e.getTranslationKey(), e.getProgramName()));
      return 0;
    }
    context.getSource().sendFeedback(
        new TranslatableText("commands.program.feedback.program_paused", programName), true);
    return 1;
  }

  private static int listPrograms(CommandContext<ServerCommandSource> context) {
    ProgramManager pm = MCCode.INSTANCE.PROGRAM_MANAGERS.get(context.getSource().getWorld());
    List<String> loadedPrograms = pm.getLoadedPrograms();
    if (loadedPrograms.isEmpty()) {
      context.getSource().sendError(new TranslatableText("commands.program.error.no_loaded_programs"));
      return 0;
    } else {
      context.getSource().sendFeedback(
          new TranslatableText("commands.program.feedback.loaded_programs",
              String.join(", ", loadedPrograms)), true);
      return loadedPrograms.size();
    }
  }

  private static int getVariableValue(CommandContext<ServerCommandSource> context) {
    ProgramManager pm = MCCode.INSTANCE.PROGRAM_MANAGERS.get(context.getSource().getWorld());
    String programName = ProgramNameArgumentType.getName(context, PROGRAM_NAME_ARG);
    Optional<Program> program = pm.getProgram(programName);
    if (program.isPresent()) {
      String variableName = ProgramVariableNameArgumentType.getName(context, VARIABLE_NAME_ARG);
      Object value;
      try {
        value = program.get().getScope().getVariable(variableName, true);
      } catch (EvaluationException e) {
        context.getSource().sendError(new TranslatableText(e.getTranslationKey(), e.getArgs()));
        return 0;
      }
      context.getSource().sendFeedback(
          new TranslatableText("commands.program.feedback.get_variable_value", variableName, value), true);
      return 1;
    } else {
      context.getSource().sendError(
          new TranslatableText("mccode.interpreter.error.program_not_found", programName));
      return 0;
    }
  }

  private static int setVariableValue(CommandContext<ServerCommandSource> context) {
    ProgramManager pm = MCCode.INSTANCE.PROGRAM_MANAGERS.get(context.getSource().getWorld());
    String programName = ProgramNameArgumentType.getName(context, PROGRAM_NAME_ARG);
    Optional<Program> program = pm.getProgram(programName);
    if (program.isPresent()) {
      String variableName = ProgramVariableNameArgumentType.getName(context, VARIABLE_NAME_ARG);
      Node node;
      try {
        node = ExpressionParser.parse(StringArgumentType.getString(context, VARIABLE_VALUE_ARG));
      } catch (SyntaxErrorException e) {
        context.getSource().sendError(new TranslatableText(e.getTranslationKey(), e.getArgs()));
        return 0;
      }

      Object value;
      try {
        value = node.evaluate(program.get().getScope(), new CallStack());
        program.get().getScope().setVariable(variableName, value, true);
      } catch (EvaluationException e) {
        context.getSource().sendError(new TranslatableText(e.getTranslationKey(), e.getArgs()));
        return 0;
      }
      context.getSource().sendFeedback(
          new TranslatableText("commands.program.feedback.set_variable_value", variableName, node), true);
      return 1;
    } else {
      context.getSource().sendError(
          new TranslatableText("mccode.interpreter.error.program_not_found", programName));
      return 0;
    }
  }

  private static int deleteVariable(CommandContext<ServerCommandSource> context) {
    ProgramManager pm = MCCode.INSTANCE.PROGRAM_MANAGERS.get(context.getSource().getWorld());
    String programName = ProgramNameArgumentType.getName(context, PROGRAM_NAME_ARG);
    Optional<Program> program = pm.getProgram(programName);
    if (program.isPresent()) {
      String variableName = ProgramVariableNameArgumentType.getName(context, VARIABLE_NAME_ARG);
      try {
        program.get().getScope().deleteVariable(variableName, true);
      } catch (EvaluationException e) {
        context.getSource().sendError(new TranslatableText(e.getTranslationKey(), e.getArgs()));
        return 0;
      }
      context.getSource().sendFeedback(
          new TranslatableText("commands.program.feedback.variable_delete", variableName), true);
      return 1;
    } else {
      context.getSource().sendError(
          new TranslatableText("mccode.interpreter.error.program_not_found", programName));
      return 0;
    }
  }

  private static int showDoc(CommandContext<ServerCommandSource> context) {
    DocType docType = context.getArgument(DOC_TYPE_ARG, DocType.class);
    String name = ProgramElementNameArgumentType.getName(context, ELEMENT_NAME_ARG);

    Optional<Pair<String, Object[]>> doc = switch (docType) {
      case TYPE -> getTypeDoc(context, name);
      case PROPERTY -> getPropertyDoc(context, name);
      case METHOD -> getMethodDoc(context, name);
      case FUNCTION -> getFunctionDoc(context, name);
    };

    if (doc.isPresent()) {
      Pair<String, Object[]> d = doc.get();
      context.getSource().sendFeedback(
          new TranslatableText("commands.program.feedback.doc_" + docType, d.getRight())
              .setStyle(Style.EMPTY.withColor(Formatting.GREEN)),
          true);
      context.getSource().sendFeedback(parseDoc(d.getLeft()), true);
      return 1;
    }
    return 0;
  }

  /**
   * Formats the given doc string.
   *
   * @param rawDoc The raw documentation string.
   * @return The resulting chat components.
   */
  private static Text parseDoc(final String rawDoc) {
    MutableText component = new LiteralText("");
    StringBuilder sb = new StringBuilder();
    boolean escapeNext = false;

    for (int i = 0; i < rawDoc.length(); i++) {
      String c = rawDoc.charAt(i) + "";
      if ("\\".equals(c) && !escapeNext) {
        escapeNext = true;
      } else if (ProgramManager.DOC_PARAM_PREFIX.equals(c)) {
        i = consumePrefixedWord(rawDoc, component, sb, i,
            param -> new LiteralText(param).setStyle(Style.EMPTY.withColor(Formatting.ITALIC)));
      } else if (ProgramManager.DOC_TYPE_PREFIX.equals(c)) {
        i = consumePrefixedWord(rawDoc, component, sb, i,
            type -> new LiteralText(type).setStyle(Style.EMPTY
                .withColor(Formatting.AQUA)
                .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/program doc type " + type.toLowerCase()))
                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText("chat.type_doc.tooltip", type.toLowerCase())))));
      } else if (ProgramManager.DOC_TAG_PREFIX.equals(c)) {
        i = consumePrefixedWord(rawDoc, component, sb, i,
            tag -> new LiteralText(tag).setStyle(Style.EMPTY.withUnderline(true)));
      } else if (ProgramManager.DOC_FUNCTION_PREFIX.equals(c)) {
        i = consumePrefixedWord(rawDoc, component, sb, i,
            function -> new LiteralText(function).setStyle(Style.EMPTY
                .withColor(Formatting.DARK_GREEN)
                .withItalic(true)
                .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/program doc function " + function.toLowerCase()))
                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText("chat.function_doc.tooltip", function.toLowerCase())))));
      } else if (ProgramManager.DOC_LITERAL_PREFIX.equals(c)) {
        i = consumePrefixedWord(rawDoc, component, sb, i,
            value -> new LiteralText(value).setStyle(Style.EMPTY.withColor(Formatting.DARK_PURPLE)));
      } else {
        sb.append(c);
      }
    }

    if (!sb.isEmpty()) {
      component.append(sb.toString());
    }

    return component;
  }

  private static final Pattern WORD_PATTERN = Pattern.compile("\\w", Pattern.UNICODE_CHARACTER_CLASS);

  private static int consumePrefixedWord(final String rawDoc, MutableText component, StringBuilder sb, final int start,
                                         final Function<String, Text> getComponent) {
    StringBuilder word = new StringBuilder();

    for (int i = start + 1; i < rawDoc.length(); i++) {
      char c = rawDoc.charAt(i);
      if (WORD_PATTERN.asPredicate().test("" + c)) {
        word.append(c);
      } else {
        break;
      }
    }

    if (word.length() != 0) {
      component.append(sb.toString());
      component.append(getComponent.apply(word.toString()));
      sb.setLength(0);
      return start + word.length();
    } else {
      sb.append(rawDoc.charAt(start));
    }

    return start;
  }

  private static Optional<Pair<String, Object[]>> getTypeDoc(CommandContext<ServerCommandSource> context, final String typeName) {
    TypeBase<?> type = ProgramManager.getTypeForName(typeName);
    if (type == null) {
      context.getSource().sendError(
          new TranslatableText("mccode.interpreter.error.no_type_for_name", typeName));
      return Optional.empty();
    }
    Optional<String> d = type.getDoc();
    if (d.isEmpty()) {
      context.getSource().sendError(
          new TranslatableText("commands.program.error.no_doc_for_type", typeName));
      return Optional.empty();
    }
    return Optional.of(new ImmutablePair<>(d.get(), new Object[]{typeName}));
  }

  private static Optional<Pair<String, Object[]>> getPropertyDoc(CommandContext<ServerCommandSource> context, final String prefixedPropertyName) {
    if (!prefixedPropertyName.contains(".")) {
      context.getSource().sendError(
          new TranslatableText("commands.program.error.invalid_property_name", prefixedPropertyName));
      return Optional.empty();
    }

    String[] parts = prefixedPropertyName.split("\\.", 2);
    String typeName = parts[0];
    String propertyName = parts[1];
    ObjectProperty property = ProgramManager.getTypeForName(typeName).getProperty(propertyName);

    if (property != null) {
      Optional<String> d = property.getDoc();
      if (d.isEmpty()) {
        context.getSource().sendError(
            new TranslatableText("commands.program.error.no_doc_for_property", typeName, propertyName));
        return Optional.empty();
      }
      return Optional.of(new ImmutablePair<>(d.get(), new Object[]{typeName, propertyName}));
    }

    context.getSource().sendError(
        new TranslatableText("commands.program.error.no_property_for_type", typeName, propertyName));
    return Optional.empty();
  }

  private static Optional<Pair<String, Object[]>> getMethodDoc(CommandContext<ServerCommandSource> context, final String prefixedMethodName) {
    if (!prefixedMethodName.contains(".")) {
      context.getSource().sendError(
          new TranslatableText("commands.program.error.invalid_method_name", prefixedMethodName));
      return Optional.empty();
    }

    String[] parts = prefixedMethodName.split("\\.", 2);
    String typeName = parts[0];
    String methodName = parts[1];
    MemberFunction method = ProgramManager.getTypeForName(typeName).getMethod(methodName);

    if (method != null) {
      Optional<String> d = method.getDoc();
      if (d.isEmpty()) {
        context.getSource().sendError(
            new TranslatableText("commands.program.error.no_doc_for_method", typeName, methodName));
        return Optional.empty();
      }
      return Optional.of(new ImmutablePair<>(d.get(), new Object[]{typeName, methodName}));
    }

    context.getSource().sendError(
        new TranslatableText("mccode.interpreter.error.no_method_for_type", typeName, methodName));
    return Optional.empty();
  }

  private static Optional<Pair<String, Object[]>> getFunctionDoc(CommandContext<ServerCommandSource> context, final String functionName) {
    BuiltinFunction function = ProgramManager.getBuiltinFunction(functionName);

    if (function != null) {
      Optional<String> d = function.getDoc();
      if (d.isEmpty()) {
        context.getSource().sendError(
            new TranslatableText("commands.program.error.no_doc_for_function", functionName));
        return Optional.empty();
      }
      return Optional.of(new ImmutablePair<>(d.get(), new Object[]{functionName}));
    }

    context.getSource().sendError(
        new TranslatableText("commands.program.error.no_function", functionName));
    return Optional.empty();
  }

  public enum DocType {
    TYPE, PROPERTY, METHOD, FUNCTION;

    @Override
    public String toString() {
      return this.name().toLowerCase();
    }
  }
}
