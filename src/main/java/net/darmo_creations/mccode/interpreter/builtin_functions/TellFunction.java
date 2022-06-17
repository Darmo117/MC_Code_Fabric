package net.darmo_creations.mccode.interpreter.builtin_functions;

import net.darmo_creations.mccode.interpreter.Parameter;
import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.Utils;
import net.darmo_creations.mccode.interpreter.annotations.Function;
import net.darmo_creations.mccode.interpreter.type_wrappers.AnyType;
import net.darmo_creations.mccode.interpreter.type_wrappers.NullType;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;
import net.minecraft.command.argument.TextArgumentType;
import net.minecraft.network.MessageType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

/**
 * A function that prints formatted text into the chat and formats any entity selector.
 */
@Function(parametersDoc = {"A value that will be interpreted as JSON data."},
    doc = "Prints text into the chat formatted from the given value interpreted as a JSON value. Behavior is similar to /tellraw command.")
public class TellFunction extends BuiltinFunction {
  /**
   * Create a function that prints formatted text into the chat.
   */
  public TellFunction() {
    super("tell", ProgramManager.getTypeInstance(NullType.class), false,
        new Parameter("message", ProgramManager.getTypeInstance(AnyType.class)));
  }

  @Override
  public Object apply(final Scope scope) {
    MinecraftServer server = scope.getProgram().getProgramManager().getWorld().getServer();
    Object message = this.getParameterValue(scope, 0);
    String command = "tellraw @s " + Utils.serializeJSON(message);
    Text text = Utils.getParsedCommandArgument(server, command, context -> TextArgumentType.getTextArgument(context, "message"));
    server.getPlayerManager().broadcast(text, MessageType.SYSTEM, Util.NIL_UUID);
    return null;
  }
}
