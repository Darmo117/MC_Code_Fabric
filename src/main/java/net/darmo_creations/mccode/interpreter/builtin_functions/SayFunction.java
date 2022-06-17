package net.darmo_creations.mccode.interpreter.builtin_functions;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.darmo_creations.mccode.interpreter.Parameter;
import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.Utils;
import net.darmo_creations.mccode.interpreter.annotations.Function;
import net.darmo_creations.mccode.interpreter.exceptions.EvaluationException;
import net.darmo_creations.mccode.interpreter.type_wrappers.NullType;
import net.darmo_creations.mccode.interpreter.type_wrappers.StringType;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;

/**
 * A function that prints text into the chat and formats any entity selector.
 */
@Function(parametersDoc = {"An entity selector that targets the players to send the message to.", "The string to print."},
    doc = "Prints a value in the chat after replacing any entity selector by the appropriate value. Behavior is similar to /say command.")
public class SayFunction extends BuiltinFunction {
  /**
   * Create a function that prints text into the chat.
   */
  public SayFunction() {
    super("say", ProgramManager.getTypeInstance(NullType.class), false,
        new Parameter("targets", ProgramManager.getTypeInstance(StringType.class)),
        new Parameter("message", ProgramManager.getTypeInstance(StringType.class), true));
  }

  @Override
  public Object apply(final Scope scope) {
    MinecraftServer server = scope.getProgram().getProgramManager().getWorld().getServer();
    String selector = this.getParameterValue(scope, 0);
    String message = this.getParameterValue(scope, 1);
    String command = "say " + message;
    Text text = Utils.getParsedCommandArgument(server, command, context -> {
      try {
        return MessageArgumentType.getMessage(context, "message");
      } catch (CommandSyntaxException e) {
        throw new RuntimeException(e);
      }
    });
    List<ServerPlayerEntity> players = Utils.getSelectedPlayers(scope.getProgram().getProgramManager().getWorld(), selector);
    if (players == null) {
      throw new EvaluationException(scope, "mccode.interpreter.error.invalid_entity_selector", selector);
    }
    players.forEach(player -> player.sendMessage(text, false));
    return null;
  }
}
