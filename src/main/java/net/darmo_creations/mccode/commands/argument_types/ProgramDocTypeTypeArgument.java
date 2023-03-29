package net.darmo_creations.mccode.commands.argument_types;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Command argument type for /program command documentation category.
 */
public class ProgramDocTypeTypeArgument implements ArgumentType<String> {
  public static ProgramDocTypeTypeArgument create() {
    return new ProgramDocTypeTypeArgument();
  }

  public static Optional<DocType> getDocType(final CommandContext<?> context, final String argumentName) {
    String name = context.getArgument(argumentName, String.class);
    for (DocType value : DocType.values()) {
      if (value.name().toLowerCase().equals(name)) {
        return Optional.of(value);
      }
    }
    return Optional.empty();
  }

  @Override
  public String parse(StringReader reader) {
    return reader.readUnquotedString();
  }

  @Override
  public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
    return CommandSource
        .suggestMatching(
            Arrays.stream(DocType.values())
                .map(v -> v.toString().toLowerCase())
                .toList(),
            builder
        );
  }

  public enum DocType {
    TYPE, PROPERTY, METHOD, FUNCTION
  }
}
