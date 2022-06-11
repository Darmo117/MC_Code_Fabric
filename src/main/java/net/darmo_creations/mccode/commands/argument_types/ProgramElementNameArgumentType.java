package net.darmo_creations.mccode.commands.argument_types;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.darmo_creations.mccode.commands.ProgramCommand;
import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.type_wrappers.TypeBase;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Command argument type for functions, methods, properties and types.
 */
public class ProgramElementNameArgumentType implements ArgumentType<String> {
  /**
   * Generate an argument type.
   */
  public static ProgramElementNameArgumentType create() {
    return new ProgramElementNameArgumentType();
  }

  public static String getName(final CommandContext<?> context, final String argumentName) {
    return context.getArgument(argumentName, String.class);
  }

  @Override
  public String parse(StringReader reader) {
    return reader.readUnquotedString();
  }

  @Override
  public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
    S source = context.getSource();
    if (source instanceof ServerCommandSource) {
      ProgramCommand.DocType docType = context.getArgument(ProgramCommand.DOC_TYPE_ARG, ProgramCommand.DocType.class);
      return switch (docType) {
        case TYPE -> CommandSource.suggestMatching(
            ProgramManager.getTypes().stream()
                .map(TypeBase::getName)
                .sorted()
                .collect(Collectors.toList()),
            builder
        );
        case PROPERTY -> CommandSource.suggestMatching(
            ProgramManager.getTypes().stream()
                .flatMap(type -> type.getProperties().keySet().stream().map(pName -> type.getName() + "." + pName))
                .sorted()
                .collect(Collectors.toList()),
            builder
        );
        case METHOD -> CommandSource.suggestMatching(
            ProgramManager.getTypes().stream()
                .flatMap(type -> type.getMethods().keySet().stream().map(mName -> type.getName() + "." + mName))
                .sorted()
                .collect(Collectors.toList()),
            builder
        );
        case FUNCTION -> CommandSource.suggestMatching(
            ProgramManager.getBuiltinFunctions().stream()
                .map(BuiltinFunction::getName)
                .sorted()
                .collect(Collectors.toList()),
            builder
        );
      };
    } else if (source instanceof ClientCommandSource s) {
      return s.getCompletions(context);
    }
    return Suggestions.empty();
  }
}
