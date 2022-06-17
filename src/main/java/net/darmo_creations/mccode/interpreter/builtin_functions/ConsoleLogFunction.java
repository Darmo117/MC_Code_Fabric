package net.darmo_creations.mccode.interpreter.builtin_functions;

import net.darmo_creations.mccode.interpreter.Parameter;
import net.darmo_creations.mccode.interpreter.Program;
import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.annotations.Function;
import net.darmo_creations.mccode.interpreter.type_wrappers.AnyType;
import net.darmo_creations.mccode.interpreter.type_wrappers.NullType;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;

/**
 * A function that prints raw text into the console.
 */
@Function(parametersDoc = {"The value to print."},
    doc = "Prints a value in the server console.")
public class ConsoleLogFunction extends BuiltinFunction {
  /**
   * Create a function that prints raw text into the console.
   */
  public ConsoleLogFunction() {
    super("console_log", ProgramManager.getTypeInstance(NullType.class), false,
        new Parameter("message", ProgramManager.getTypeInstance(AnyType.class), true));
  }

  @Override
  public Object apply(final Scope scope) {
    Program program = scope.getProgram();
    MinecraftServer server = program.getProgramManager().getWorld().getServer();
    Object message = this.getParameterValue(scope, 0);
    String text = ProgramManager.getTypeForValue(message).toString(message);
    //noinspection OptionalGetWithoutIsPresent
    String dimension = scope.getProgram().getProgramManager().getWorld().method_40134().getKey().get().getValue().toString();
    server.sendSystemMessage(new LiteralText("[MCCode:%s][%s] %s".formatted(program.getName(), dimension, text)), Util.NIL_UUID);
    return null;
  }
}
