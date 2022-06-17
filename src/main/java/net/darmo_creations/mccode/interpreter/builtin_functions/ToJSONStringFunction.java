package net.darmo_creations.mccode.interpreter.builtin_functions;

import net.darmo_creations.mccode.interpreter.Parameter;
import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.Utils;
import net.darmo_creations.mccode.interpreter.annotations.Function;
import net.darmo_creations.mccode.interpreter.type_wrappers.AnyType;
import net.darmo_creations.mccode.interpreter.type_wrappers.MapType;
import net.darmo_creations.mccode.interpreter.type_wrappers.StringType;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;

/**
 * Converts a map into a JSON string.
 */
@Function(parametersDoc = {"The value to serialize. May be #null."},
    doc = "Converts a value into a JSON string.")
public class ToJSONStringFunction extends BuiltinFunction {
  public ToJSONStringFunction() {
    super("to_json_string", ProgramManager.getTypeInstance(StringType.class), false,
        new Parameter("v", ProgramManager.getTypeInstance(AnyType.class), true));
  }

  @Override
  public Object apply(final Scope scope) {
    return Utils.serializeJSON(this.getParameterValue(scope, 0));
  }
}
