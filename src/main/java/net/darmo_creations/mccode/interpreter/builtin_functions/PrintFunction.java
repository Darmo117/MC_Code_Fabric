package net.darmo_creations.mccode.interpreter.builtin_functions;

import net.darmo_creations.mccode.interpreter.Parameter;
import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.annotations.Function;
import net.darmo_creations.mccode.interpreter.type_wrappers.AnyType;
import net.darmo_creations.mccode.interpreter.type_wrappers.NullType;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;

/**
 * A function that prints raw text into the chat.
 */
@Function(parametersDoc = {"The value to print."},
    doc = "Prints a value in the chat.")
public class PrintFunction extends BuiltinFunction {
  /**
   * Create a function that prints raw text into the chat.
   */
  public PrintFunction() {
    super("print", ProgramManager.getTypeInstance(NullType.class), false,
        new Parameter("message", ProgramManager.getTypeInstance(AnyType.class), true));
  }

  @Override
  public Object apply(final Scope scope) {
    Object message = this.getParameterValue(scope, 0);
    String text = ProgramManager.getTypeForValue(message).toString(message);
//    scope.getProgram().getProgramManager().getWorld().getPlayers(p -> true)
//        .forEach(player -> player.sendMessage(new LiteralText(text), false));
    scope.getProgram().getProgramManager().getWorld().getServer().getPlayerManager().broadcast(new LiteralText(text), MessageType.SYSTEM, Util.NIL_UUID);
    return null;
  }
}
