package net.darmo_creations.mccode.commands.argument_types;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.text.TranslatableText;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Command argument type that wraps an enumeration.
 *
 * @param <T> Enumeration’s type.
 */
public class EnumArgumentType<T extends Enum<T>> implements ArgumentType<T> {
  /**
   * Creates an argument type for the given enum class.
   *
   * @param enumClass Enum’s class.
   * @param <T1>      Enum’s type.
   * @return An argument type.
   */
  public static <T1 extends Enum<T1>> EnumArgumentType<T1> of(final Class<T1> enumClass) {
    return new EnumArgumentType<>(enumClass);
  }

  private final Class<T> enumClass;

  private EnumArgumentType(Class<T> enumClass) {
    this.enumClass = enumClass;
  }

  @Override
  public T parse(StringReader reader) throws CommandSyntaxException {
    String arg = reader.readUnquotedString();
    T[] values;
    try {
      //noinspection unchecked
      values = (T[]) this.enumClass.getMethod("values").invoke(null);
    } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
      throw new CommandSyntaxException(null, new TranslatableText("commands.program.error.invalid_argument", arg));
    }
    return Arrays.stream(values).filter(t -> t.toString().equals(arg)).findAny().orElseThrow(
        () -> new CommandSyntaxException(null, new TranslatableText("commands.program.error.invalid_argument", arg)));
  }

  @Override
  public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
    List<String> values = new LinkedList<>();
    return CommandSource.suggestMatching(values, builder);
  }
}
