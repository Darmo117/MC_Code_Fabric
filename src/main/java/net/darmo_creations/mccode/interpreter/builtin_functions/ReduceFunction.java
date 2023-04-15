package net.darmo_creations.mccode.interpreter.builtin_functions;

import net.darmo_creations.mccode.interpreter.CallStack;
import net.darmo_creations.mccode.interpreter.Parameter;
import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.annotations.Function;
import net.darmo_creations.mccode.interpreter.exceptions.TypeException;
import net.darmo_creations.mccode.interpreter.nodes.FunctionCallNode;
import net.darmo_creations.mccode.interpreter.type_wrappers.AnyType;
import net.darmo_creations.mccode.interpreter.type_wrappers.FunctionType;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;

import java.util.Arrays;

/**
 * Function similar to Python’s funtools.reduce() function.
 */
@Function(parametersDoc = {"An iterable.",
    "A `function that combines the accumulator (1st arg.) with a value from the iterable (2nd arg.).",
    "Initial value of the accumulator."},
    doc = "Applies the given reduce operation on an iterable object starting with the given accumulator value.")
public class ReduceFunction extends BuiltinFunction {
  /**
   * Create a function that applies the given reduce operation on an iterable object
   * starting with the given accumulator value.
   */
  public ReduceFunction() {
    super("reduce", ProgramManager.getTypeInstance(AnyType.class), false, false,
        new Parameter("it", ProgramManager.getTypeInstance(AnyType.class)),
        new Parameter("f", ProgramManager.getTypeInstance(FunctionType.class)),
        new Parameter("init", ProgramManager.getTypeInstance(AnyType.class)));
  }

  @Override
  public Object apply(final Scope scope, CallStack callStack) {
    Object iterable = this.getParameterValue(scope, 0);
    net.darmo_creations.mccode.interpreter.types.Function f = this.getParameterValue(scope, 1);
    Object acc = this.getParameterValue(scope, 2);
    if (iterable instanceof Iterable<?> it) {
      // Cannot use Stream.reduce() due to generic types shenanigans
      for (Object v : it) {
        acc = FunctionCallNode.applyFunction(f, Arrays.asList(acc, v), scope, callStack, -1, -1);
      }
      return acc;
    }
    throw new TypeException("expected iterable, got %s".formatted(ProgramManager.getTypeForValue(iterable)));
  }
}
