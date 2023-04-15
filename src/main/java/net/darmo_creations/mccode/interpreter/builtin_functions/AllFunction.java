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
 * Function equivalent to Python’s all() function.
 */
@Function(parametersDoc = {"An iterable.", "A function that returns true or false for a given value."},
    doc = "Returns true if the provided function returns a truthy value for all values of the given iterable" +
        " or if the iterable is empty; false otherwise.")
public class AllFunction extends BuiltinFunction {
  /**
   * Create a function that returns true if the provided function
   * returns a truthy value for all values of the given iterable.
   */
  public AllFunction() {
    super("all", ProgramManager.getTypeInstance(BooleanType.class), false, false,
        new Parameter("it", ProgramManager.getTypeInstance(AnyType.class)),
        new Parameter("p", ProgramManager.getTypeInstance(FunctionType.class)));
  }

  @Override
  public Object apply(final Scope scope, CallStack callStack) {
    Object iterable = this.getParameterValue(scope, 0);
    net.darmo_creations.mccode.interpreter.types.Function f = this.getParameterValue(scope, 1);
    if (iterable instanceof Iterable<?> it) {
      return Streams.stream(it).allMatch(v -> {
        Object r = FunctionCallNode.callFunction(f, Collections.singletonList(v), scope, callStack, -1, -1);
        return ProgramManager.getTypeForValue(r).toBoolean(r);
      });
    }
    throw new TypeException("expected iterable, got %s".formatted(ProgramManager.getTypeForValue(iterable)));
  }
}
