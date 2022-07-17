package net.darmo_creations.mccode.interpreter.builtin_functions;

import net.darmo_creations.mccode.interpreter.CallStack;
import net.darmo_creations.mccode.interpreter.Parameter;
import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.annotations.Function;
import net.darmo_creations.mccode.interpreter.type_wrappers.FloatType;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;

/**
 * Extension of {@link Math#hypot(double, double)} to 3D.
 */
@Function(parametersDoc = {"A number.", "A number.", "A number."},
    doc = "Returns %sqrt($x² + $y² + $z²).")
public class Hypot3Function extends BuiltinFunction {
  /**
   * Create a function that returns sqrt(x² + y² + z²).
   */
  public Hypot3Function() {
    super("hypot3", ProgramManager.getTypeInstance(FloatType.class), false,
        new Parameter("x", ProgramManager.getTypeInstance(FloatType.class)),
        new Parameter("y", ProgramManager.getTypeInstance(FloatType.class)),
        new Parameter("z", ProgramManager.getTypeInstance(FloatType.class)));
  }

  @Override
  public Object apply(final Scope scope, CallStack callStack) {
    double x = this.getParameterValue(scope, 0);
    double y = this.getParameterValue(scope, 1);
    double z = this.getParameterValue(scope, 2);
    return Math.sqrt(x * x + y * y + z * z);
  }
}
