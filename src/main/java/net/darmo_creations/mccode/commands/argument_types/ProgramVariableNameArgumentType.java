package net.darmo_creations.mccode.commands.argument_types;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.darmo_creations.mccode.MCCode;
import net.darmo_creations.mccode.commands.ProgramCommand;
import net.darmo_creations.mccode.interpreter.Program;
import net.darmo_creations.mccode.interpreter.Variable;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Command argument type for variables names of a specific program.
 */
public class ProgramVariableNameArgumentType implements ArgumentType<String> {
  /**
   * Generate an argument type for any variable.
   */
  public static ProgramVariableNameArgumentType variableName() {
    return new ProgramVariableNameArgumentType(VariableType.ANY);
  }

  /**
   * Generate an argument type for an editable variable.
   */
  public static ProgramVariableNameArgumentType editableVariableName() {
    return new ProgramVariableNameArgumentType(VariableType.EDITABLE);
  }

  /**
   * Generate an argument type for a deletable variable.
   */
  public static ProgramVariableNameArgumentType deletableVariableName() {
    return new ProgramVariableNameArgumentType(VariableType.DELETABLE);
  }

  public static String getName(final CommandContext<?> context, final String argumentName) {
    return context.getArgument(argumentName, String.class);
  }

  private final VariableType variableType;

  private ProgramVariableNameArgumentType(final VariableType variableType) {
    this.variableType = variableType;
  }

  @Override
  public String parse(StringReader reader) {
    return reader.readUnquotedString();
  }

  @Override
  public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
    S source = context.getSource();
    if (source instanceof ServerCommandSource s) {
      String programName = StringArgumentType.getString(context, ProgramCommand.PROGRAM_NAME_ARG);
      Optional<Program> program = MCCode.INSTANCE.PROGRAM_MANAGERS.get(s.getWorld()).getProgram(programName);
      if (program.isPresent()) {
        Predicate<Variable> filter = switch (this.variableType) {
          case ANY -> Variable::isPubliclyVisible;
          case EDITABLE -> v -> v.isPubliclyVisible() && v.isEditableFromOutside() && !v.isConstant();
          case DELETABLE -> v -> v.isPubliclyVisible() && v.isDeletable();
        };
        return CommandSource.suggestMatching(
            program.get().getScope().getVariables().values().stream()
                .filter(filter)
                .map(Variable::getName)
                .collect(Collectors.toList()),
            builder
        );
      }
    } else if (source instanceof ClientCommandSource s) {
      return s.getCompletions(context);
    }
    return Suggestions.empty();
  }

  private enum VariableType {
    ANY, EDITABLE, DELETABLE
  }
}
