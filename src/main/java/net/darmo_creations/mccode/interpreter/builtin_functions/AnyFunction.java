package net.darmo_creations.mccode.interpreter.builtin_functions;

import com.google.common.collect.Streams;
import net.darmo_creations.mccode.interpreter.CallStack;
import net.darmo_creations.mccode.interpreter.Parameter;
import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.annotations.Function;
import net.darmo_creations.mccode.interpreter.exceptions.TypeException;
import net.darmo_creations.mccode.interpreter.nodes.FunctionCallNode;
import net.darmo_creations.mccode.interpreter.type_wrappers.AnyType;
import net.darmo_creations.mccode.interpreter.type_wrappers.BooleanType;
import net.darmo_creations.mccode.interpreter.type_wrappers.FunctionType;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;

import java.util.Collections;

/**
 * Function similar to Python’s any() function.
 */
@Function(parametersDoc = {"An iterable.", "A predicate `function that returns #true or #false for a given value."},
    doc = "Returns #true if the provided `function returns a truthy value for any value of the given iterable;" +
        " #false otherwise or if the iterable is empty.")
public class AnyFunction extends BuiltinFunction {
  /**
   * Create a function that returns true if the provided function
   * returns a truthy value for any value of the given iterable.
   */
  public AnyFunction() {
    super("any", ProgramManager.getTypeInstance(BooleanType.class), false, false,
        new Parameter("it", ProgramManager.getTypeInstance(AnyType.class)),
        new Parameter("p", ProgramManager.getTypeInstance(FunctionType.class)));
  }

  @Override
  public Object apply(final Scope scope, CallStack callStack) {
    Object iterable = this.getParameterValue(scope, 0);
    net.darmo_creations.mccode.interpreter.types.Function f = this.getParameterValue(scope, 1);
    if (iterable instanceof Iterable<?> it) {
      return Streams.stream(it).anyMatch(v -> {
        Object r = FunctionCallNode.applyFunction(f, Collections.singletonList(v), scope, callStack, -1, -1);
        return ProgramManager.getTypeForValue(r).toBoolean(r);
      });
    }
    throw new TypeException("expected iterable, got %s".formatted(ProgramManager.getTypeForValue(iterable)));
  }
}
