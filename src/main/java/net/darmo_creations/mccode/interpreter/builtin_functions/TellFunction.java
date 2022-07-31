package net.darmo_creations.mccode.interpreter.builtin_functions;

import net.darmo_creations.mccode.interpreter.*;
import net.darmo_creations.mccode.interpreter.annotations.Function;
import net.darmo_creations.mccode.interpreter.exceptions.EvaluationException;
import net.darmo_creations.mccode.interpreter.type_wrappers.AnyType;
import net.darmo_creations.mccode.interpreter.type_wrappers.NullType;
import net.darmo_creations.mccode.interpreter.type_wrappers.StringType;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;
import net.minecraft.command.argument.TextArgumentType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;

/**
 * A function that prints formatted text into the chat and formats any entity selector.
 */
@Function(parametersDoc = {"An entity selector that targets the players to send the message to.", "A value that will be interpreted as JSON data. May be #null."},
    doc = "Prints text into the chat, formatted from the given object interpreted as a JSON-like value. Behavior is similar to /tellraw command.")
public class TellFunction extends BuiltinFunction {
  /**
   * Create a function that prints formatted text into the chat.
   */
  public TellFunction() {
    super("tell", ProgramManager.getTypeInstance(NullType.class), false, false,
        new Parameter("targets", ProgramManager.getTypeInstance(StringType.class)),
        new Parameter("message", ProgramManager.getTypeInstance(AnyType.class), true));
  }

  @Override
  public Object apply(final Scope scope, CallStack callStack) {
    String selector = this.getParameterValue(scope, 0);
    List<ServerPlayerEntity> players = Utils.getSelectedPlayers(scope.getProgram().getProgramManager().getWorld(), selector);
    if (players == null) {
      throw new EvaluationException(scope, "mccode.interpreter.error.invalid_player_selector", selector);
    }
    String command = "tellraw @s " + Utils.serializeJSON(this.getParameterValue(scope, 1));
    MinecraftServer server = scope.getProgram().getProgramManager().getWorld().getServer();
    Text text = Utils.getParsedCommandArgument(server, command, context -> TextArgumentType.getTextArgument(context, "message"));
    players.forEach(player -> player.sendMessage(text, false));
    return null;
  }
}
