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
import net.darmo_creations.mccode.interpreter.type_wrappers.FunctionType;
import net.darmo_creations.mccode.interpreter.type_wrappers.ListType;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;
import net.darmo_creations.mccode.interpreter.types.MCList;

import java.util.Collections;

/**
 * Function similar to Python’s map() function.
 */
@Function(parametersDoc = {"An iterable.", "A `function that takes in a value and returns a value."},
    returnDoc = "A new `list object containing the transformed elements.",
    doc = "Applies the given `function to each element of the given iterable.")
public class MapFunction extends BuiltinFunction {
  /**
   * Create a function that applies the given function to each element of the given iterable.
   */
  public MapFunction() {
    super("map", ProgramManager.getTypeInstance(ListType.class), false, false,
        new Parameter("it", ProgramManager.getTypeInstance(AnyType.class)),
        new Parameter("f", ProgramManager.getTypeInstance(FunctionType.class)));
  }

  @Override
  public Object apply(final Scope scope, CallStack callStack) {
    Object iterable = this.getParameterValue(scope, 0);
    net.darmo_creations.mccode.interpreter.types.Function f = this.getParameterValue(scope, 1);
    if (iterable instanceof Iterable<?> it) {
      return Streams.stream(it)
          .map(v -> FunctionCallNode.applyFunction(f, Collections.singletonList(v), scope, callStack, -1, -1))
          .collect(MCList::new, MCList::add, MCList::addAll);
    }
    throw new TypeException("expected iterable, got %s".formatted(ProgramManager.getTypeForValue(iterable)));
  }
}
