package net.darmo_creations.mccode.interpreter.builtin_functions;

import net.darmo_creations.mccode.interpreter.*;
import net.darmo_creations.mccode.interpreter.annotations.Function;
import net.darmo_creations.mccode.interpreter.type_wrappers.StringType;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;

/**
 * Unescapes a string.
 *
 * @see Utils#unescapeString(String)
 */
@Function(parametersDoc = {"The string to unescape."},
    doc = "Unescapes a string by removing any \\ character before a special character (\", \\ or \\n) and removing the surrounding quotes (\").")
public class UnescapeFunction extends BuiltinFunction {
  public UnescapeFunction() {
    super("unescape", ProgramManager.getTypeInstance(StringType.class), false,
        new Parameter("s", ProgramManager.getTypeInstance(StringType.class)));
  }

  @Override
  public Object apply(final Scope scope, CallStack callStack) {
    return Utils.unescapeString(this.getParameterValue(scope, 0));
  }
}
