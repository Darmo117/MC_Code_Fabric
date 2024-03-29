package net.darmo_creations.mccode.interpreter.builtin_functions;

import net.darmo_creations.mccode.interpreter.*;
import net.darmo_creations.mccode.interpreter.annotations.Function;
import net.darmo_creations.mccode.interpreter.exceptions.CastException;
import net.darmo_creations.mccode.interpreter.type_wrappers.AnyType;
import net.darmo_creations.mccode.interpreter.type_wrappers.BlockType;
import net.darmo_creations.mccode.interpreter.type_wrappers.MapType;
import net.darmo_creations.mccode.interpreter.type_wrappers.StringType;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;
import net.darmo_creations.mccode.interpreter.types.MCMap;
import net.minecraft.block.Block;

import java.util.stream.Collectors;

/**
 * Formats block-related objects into a string format parsable by commands.
 */
@Function(parametersDoc = {
    "A block object block ID.",
    "A `map object to serve as the blockstate. May be #null.",
    "A `map object to serve as the tags. May be #null."
},
    doc = "Converts the given block-related objects into a string that can be used in commands.")
public class FormatBlockFunction extends BuiltinFunction {
  public FormatBlockFunction() {
    super("format_block", ProgramManager.getTypeInstance(StringType.class), false, false,
        new Parameter("block", ProgramManager.getTypeInstance(AnyType.class)),
        new Parameter("state", ProgramManager.getTypeInstance(MapType.class), true),
        new Parameter("tags", ProgramManager.getTypeInstance(MapType.class), true));
  }

  @Override
  public Object apply(final Scope scope, CallStack callStack) {
    Object block = this.getParameterValue(scope, 0);
    String id;
    if (block instanceof Block b) {
      id = ProgramManager.getTypeInstance(BlockType.class).getID(b);
    } else if (block instanceof String s) {
      id = s;
    } else {
      throw new CastException(scope, ProgramManager.getTypeInstance(BlockType.class), ProgramManager.getTypeForValue(block));
    }
    MCMap blockState = this.getParameterValue(scope, 1);
    if (blockState != null) {
      id += blockState.entrySet().stream()
          .map(e -> e.getKey() + "=" + e.getValue())
          .collect(Collectors.joining(",", "[", "]"));
    }
    MCMap tags = this.getParameterValue(scope, 2);
    return id + Utils.formatDataTags(tags);
  }
}
