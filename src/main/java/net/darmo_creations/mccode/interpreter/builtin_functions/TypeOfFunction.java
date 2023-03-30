package net.darmo_creations.mccode.interpreter.builtin_functions;

import net.darmo_creations.mccode.interpreter.CallStack;
import net.darmo_creations.mccode.interpreter.Parameter;
import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.annotations.Function;
import net.darmo_creations.mccode.interpreter.type_wrappers.AnyType;
import net.darmo_creations.mccode.interpreter.type_wrappers.StringType;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;

/**
 * Function that returns a string that corresponds to the type of the given value.
 */
@Function(parametersDoc = {"The value to get the type of."},
    returnDoc = "A `string.",
    doc = "Returns a `string that corresponds to the type of the given value.")
public class TypeOfFunction extends BuiltinFunction {
  /**
   * Create a function that returns a string that corresponds to the type of the given value.
   */
  public TypeOfFunction() {
    super("type_of", ProgramManager.getTypeInstance(StringType.class), false, false,
        new Parameter("o", ProgramManager.getTypeInstance(AnyType.class), true));
  }

  @Override
  public Object apply(final Scope scope, CallStack callStack) {
    return ProgramManager.getTypeForValue(this.getParameterValue(scope, 0)).getName();
  }
}
