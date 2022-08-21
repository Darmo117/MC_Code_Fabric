package net.darmo_creations.mccode.interpreter.builtin_functions;

import net.darmo_creations.mccode.interpreter.*;
import net.darmo_creations.mccode.interpreter.annotations.Function;
import net.darmo_creations.mccode.interpreter.type_wrappers.StringType;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;

/**
 * Escapes a string.
 *
 * @see Utils#escapeString(String)
 */
@Function(parametersDoc = {"The string to escape."},
    returnDoc = "The escaped string.",
    doc = "Escapes a string by inserting a \\ character before any special character (\", \\ or \\n) and surrounding the whole string with quotes (\").")
public class EscapeFunction extends BuiltinFunction {
  public EscapeFunction() {
    super("escape", ProgramManager.getTypeInstance(StringType.class), false, false,
        new Parameter("s", ProgramManager.getTypeInstance(StringType.class)));
  }

  @Override
  public Object apply(final Scope scope, CallStack callStack) {
    return Utils.escapeString(this.getParameterValue(scope, 0));
  }
}
