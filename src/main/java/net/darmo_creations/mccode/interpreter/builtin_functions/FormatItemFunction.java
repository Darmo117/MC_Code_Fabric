package net.darmo_creations.mccode.interpreter.builtin_functions;

import net.darmo_creations.mccode.interpreter.Parameter;
import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.Utils;
import net.darmo_creations.mccode.interpreter.annotations.Function;
import net.darmo_creations.mccode.interpreter.exceptions.CastException;
import net.darmo_creations.mccode.interpreter.type_wrappers.AnyType;
import net.darmo_creations.mccode.interpreter.type_wrappers.ItemType;
import net.darmo_creations.mccode.interpreter.type_wrappers.MapType;
import net.darmo_creations.mccode.interpreter.type_wrappers.StringType;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;
import net.darmo_creations.mccode.interpreter.types.MCMap;
import net.minecraft.item.Item;

/**
 * Formats item-related objects into a string format parsable by commands.
 */
@Function(parametersDoc = {"An item ID.", "A `map object to serve as the tags. May be #null."},
    doc = "Converts the given item-related objects into a string that can be used in commands.")
public class FormatItemFunction extends BuiltinFunction {
  public FormatItemFunction() {
    super("format_item", ProgramManager.getTypeInstance(StringType.class), false,
        new Parameter("item", ProgramManager.getTypeInstance(AnyType.class)),
        new Parameter("tags", ProgramManager.getTypeInstance(MapType.class), true));
  }

  @Override
  public Object apply(final Scope scope) {
    Object item = this.getParameterValue(scope, 0);
    String id;
    if (item instanceof Item i) {
      id = ProgramManager.getTypeInstance(ItemType.class).getID(i);
    } else if (item instanceof String s) {
      id = s;
    } else {
      throw new CastException(scope, ProgramManager.getTypeInstance(ItemType.class), ProgramManager.getTypeForValue(item));
    }
    MCMap tags = this.getParameterValue(scope, 1);
    return id + Utils.formatDataTags(tags);
  }
}
