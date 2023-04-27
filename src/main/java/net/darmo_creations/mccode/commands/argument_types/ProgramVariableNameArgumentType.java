package net.darmo_creations.mccode.commands.argument_types;

import com.google.gson.JsonObject;
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
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.serialize.ArgumentSerializer;
import net.minecraft.network.PacketByteBuf;
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
      Optional<Program> program = MCCode.INSTANCE.getProgramManager(s.getWorld()).getProgram(programName);
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

  public enum VariableType {
    ANY, EDITABLE, DELETABLE
  }

  public static final class Serializer implements ArgumentSerializer<ProgramVariableNameArgumentType, Serializer.Properties> {
    @Override
    public void writePacket(Properties properties, PacketByteBuf buf) {
      buf.writeInt(properties.variableType.ordinal());
    }

    @Override
    public Properties fromPacket(PacketByteBuf buf) {
      VariableType[] values = VariableType.values();
      return new Properties(values[buf.readInt() % values.length]);
    }

    @Override
    public void writeJson(Properties properties, JsonObject json) {
      json.addProperty("variable_type", properties.variableType.ordinal());
    }

    @Override
    public Properties getArgumentTypeProperties(ProgramVariableNameArgumentType argumentType) {
      return new Properties(argumentType.variableType);
    }

    public final class Properties implements ArgumentSerializer.ArgumentTypeProperties<ProgramVariableNameArgumentType> {
      private final VariableType variableType;

      Properties(VariableType variableType) {
        this.variableType = variableType;
      }

      @Override
      public ProgramVariableNameArgumentType createType(CommandRegistryAccess commandRegistryAccess) {
        return new ProgramVariableNameArgumentType(this.variableType);
      }

      @Override
      public ArgumentSerializer<ProgramVariableNameArgumentType, ?> getSerializer() {
        return Serializer.this;
      }
    }
  }
}
