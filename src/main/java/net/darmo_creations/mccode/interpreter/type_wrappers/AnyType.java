package net.darmo_creations.mccode.interpreter.type_wrappers;

import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.annotations.Type;
import net.darmo_creations.mccode.interpreter.exceptions.MCCodeException;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;

/**
 * Wrapper type for {@link Object} class.
 * <p>
 * It does not have a cast operator.
 */
@Type(name = AnyType.NAME,
    generateCastOperator = false,
    doc = "The base type. It is a placeholder used to denote that a method/function may accept/return values of any type.")
public class AnyType extends TypeBase<Object> {
  public static final String NAME = "any";

  @Override
  public Class<Object> getWrappedType() {
    return Object.class;
  }

  @Override
  public Object implicitCast(final Scope scope, final Object o) {
    return o;
  }

  @Override
  public CompoundTag _writeToTag(final Object self) {
    throw new MCCodeException("cannot serialize objects of type any");
  }

  @Override
  public Object readFromTag(final Scope scope, final CompoundTag tag) {
    throw new MCCodeException("cannot deserialize objects of type any");
  }
}
