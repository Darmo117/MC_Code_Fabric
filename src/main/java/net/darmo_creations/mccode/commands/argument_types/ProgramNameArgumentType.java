package net.darmo_creations.mccode.commands.argument_types;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.darmo_creations.mccode.MCCode;
import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.serialize.ArgumentSerializer;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.command.ServerCommandSource;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Command argument type for program names.
 */
public class ProgramNameArgumentType implements ArgumentType<String> {
  /**
   * Generate an argument type for programs available in save’s data directory only.
   */
  public static ProgramNameArgumentType available() {
    return new ProgramNameArgumentType(false);
  }

  /**
   * Generate an argument type for loaded programs only.
   */
  public static ProgramNameArgumentType loaded() {
    return new ProgramNameArgumentType(true);
  }

  private final boolean loadedOnly;

  private ProgramNameArgumentType(final boolean loadedOnly) {
    this.loadedOnly = loadedOnly;
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
    if (source instanceof ServerCommandSource s) {
      ProgramManager pm = MCCode.instance().getProgramManager(s.getWorld());
      if (!this.loadedOnly) {
        File dir = pm.getProgramsDirectory();
        if (!dir.isDirectory()) {
          return Suggestions.empty();
        }
        //noinspection ConstantConditions
        List<String> names = Arrays.stream(dir.listFiles(f -> f.getName().endsWith(".mccode")))
            .map(f -> f.getName().substring(0, f.getName().indexOf('.')))
            .collect(Collectors.toList());
        return CommandSource.suggestMatching(names, builder);
      } else {
        return CommandSource.suggestMatching(pm.getLoadedPrograms(), builder);
      }
    } else if (source instanceof ClientCommandSource s) {
      return s.getCompletions(context);
    }
    return Suggestions.empty();
  }

  public static final class Serializer implements ArgumentSerializer<ProgramNameArgumentType, Serializer.Properties> {
    @Override
    public void writePacket(Properties properties, PacketByteBuf buf) {
      buf.writeBoolean(properties.loadedOnly);
    }

    @Override
    public Properties fromPacket(PacketByteBuf buf) {
      return new Properties(buf.readBoolean());
    }

    @Override
    public void writeJson(Properties properties, JsonObject json) {
      json.addProperty("loaded_only", properties.loadedOnly);
    }

    @Override
    public Properties getArgumentTypeProperties(ProgramNameArgumentType argumentType) {
      return new Properties(argumentType.loadedOnly);
    }

    public final class Properties implements ArgumentSerializer.ArgumentTypeProperties<ProgramNameArgumentType> {
      private final boolean loadedOnly;

      Properties(boolean loadedOnly) {
        this.loadedOnly = loadedOnly;
      }

      @Override
      public ProgramNameArgumentType createType(CommandRegistryAccess commandRegistryAccess) {
        return new ProgramNameArgumentType(this.loadedOnly);
      }

      @Override
      public ArgumentSerializer<ProgramNameArgumentType, ?> getSerializer() {
        return Serializer.this;
      }
    }
  }
}
