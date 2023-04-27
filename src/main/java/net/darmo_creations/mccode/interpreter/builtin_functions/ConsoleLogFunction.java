package net.darmo_creations.mccode.interpreter.builtin_functions;

import net.darmo_creations.mccode.interpreter.*;
import net.darmo_creations.mccode.interpreter.annotations.Function;
import net.darmo_creations.mccode.interpreter.type_wrappers.AnyType;
import net.darmo_creations.mccode.interpreter.type_wrappers.NullType;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * A function that prints raw text into the console.
 */
@Function(parametersDoc = {"The values to print."},
    doc = "Prints values to the server console.")
public class ConsoleLogFunction extends BuiltinFunction {
  /**
   * Create a function that prints raw text into the console.
   */
  public ConsoleLogFunction() {
    super("console_log", ProgramManager.getTypeInstance(NullType.class), false, true,
        new Parameter("message", ProgramManager.getTypeInstance(AnyType.class), true));
  }

  @Override
  public Object apply(final Scope scope, CallStack callStack) {
    Program program = scope.getProgram();
    MinecraftServer server = program.getProgramManager().getWorld().getServer();
    Object[] message = this.getParameterValue(scope, 0);
    String text = Arrays.stream(message)
        .map(o -> ProgramManager.getTypeForValue(o).toString(o))
        .collect(Collectors.joining(" "));
    String dimension = Utils.getDimension(scope.getProgram().getProgramManager().getWorld());
    server.sendMessage(Text.literal("[MCCode:Program %s in %s] %s".formatted(program.getName(), dimension, text)));
    return null;
  }
}
