package net.darmo_creations.mccode.interpreter.builtin_functions;

import net.darmo_creations.mccode.interpreter.CallStack;
import net.darmo_creations.mccode.interpreter.Parameter;
import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.annotations.Function;
import net.darmo_creations.mccode.interpreter.type_wrappers.AnyType;
import net.darmo_creations.mccode.interpreter.type_wrappers.FloatType;
import net.darmo_creations.mccode.interpreter.type_wrappers.PosType;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;
import net.darmo_creations.mccode.interpreter.types.MCList;
import net.darmo_creations.mccode.interpreter.types.Position;

import java.util.Arrays;

/**
 * A function that creates a new {@link Position} instance.
 */
@Function(parametersDoc = {"X component.", "Y component.", "Z component."},
    doc = "Creates a `pos object.")
public class PosFunction extends BuiltinFunction {
  /**
   * Create a function that creates a new Position instance.
   */
  public PosFunction() {
    super("pos", ProgramManager.getTypeInstance(FloatType.class), false,
        new Parameter("x", ProgramManager.getTypeInstance(AnyType.class)),
        new Parameter("y", ProgramManager.getTypeInstance(AnyType.class)),
        new Parameter("z", ProgramManager.getTypeInstance(AnyType.class)));
  }

  @Override
  public Object apply(final Scope scope, CallStack callStack) {
    return ProgramManager.getTypeInstance(PosType.class).explicitCast(scope, new MCList(Arrays.asList(
        this.getParameterValue(scope, 0),
        this.getParameterValue(scope, 1),
        this.getParameterValue(scope, 2)
    )));
  }
}
