package net.darmo_creations.mccode.interpreter.type_wrappers;

import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.annotations.Type;
import net.darmo_creations.mccode.interpreter.exceptions.MCCodeException;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;
import net.darmo_creations.mccode.interpreter.types.Function;
import net.darmo_creations.mccode.interpreter.types.UserFunction;

/**
 * Wrapper for {@link Function} class.
 * <p>
 * It does not have a cast operator.
 */
@Type(name = FunctionType.NAME,
    generateCastOperator = false,
    doc = "Represents a function. A function is a callable object that takes arguments and returns a value.")
public class FunctionType extends TypeBase<Function> {
  public static final String NAME = "function";

  private static final String FUNCTION_TYPE_KEY = "Type";
  private static final String FUNCTION_KEY = "Function";

  private static final String FUNCTION_TYPE_BUILTIN = "builtin";
  private static final String FUNCTION_TYPE_USER = "user";

  @Override
  public Class<Function> getWrappedType() {
    return Function.class;
  }

  @Override
  protected CompoundTag _writeToTag(final Function self) {
    CompoundTag tag = super._writeToTag(self);
    if (self instanceof BuiltinFunction) {
      tag.putString(FUNCTION_TYPE_KEY, FUNCTION_TYPE_BUILTIN);
      tag.putString(FUNCTION_KEY, self.getName());
    } else if (self instanceof UserFunction) {
      tag.putString(FUNCTION_TYPE_KEY, FUNCTION_TYPE_USER);
      CompoundTag functionTag = ((UserFunction) self).writeToNBT();
      tag.putTag(FUNCTION_KEY, functionTag);
    }
    return tag;
  }

  @Override
  public Function readFromTag(final Scope scope, final CompoundTag tag) {
    String functionType = tag.getString(FUNCTION_TYPE_KEY);
    return switch (functionType) {
      // Type-safe as builtin functions cannot be deleted nor overridden
      case FUNCTION_TYPE_BUILTIN -> (Function) scope.getVariable(tag.getString(FUNCTION_KEY), false);
      case FUNCTION_TYPE_USER -> new UserFunction(tag.getCompound(FUNCTION_KEY));
      default -> throw new MCCodeException("invalid function type " + functionType);
    };
  }
}
