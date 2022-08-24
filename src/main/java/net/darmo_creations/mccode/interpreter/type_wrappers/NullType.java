package net.darmo_creations.mccode.interpreter.type_wrappers;

import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.annotations.Type;
import net.darmo_creations.mccode.interpreter.exceptions.CastException;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;

/**
 * Wrapper type for {@link Void} class (null value).
 * <p>
 * It does not have a cast operator.
 */
@Type(name = NullType.NAME,
    generateCastOperator = false,
    doc = "This type has a single value, #null which represents the absence of any value.\n" +
        "When evaluated as a `boolean, it always yields #false.")
public class NullType extends TypeBase<Void> {
  public static final String NAME = "null";

  @Override
  public Class<Void> getWrappedType() {
    return Void.class;
  }

  @Override
  protected boolean __bool__(final Void self) {
    return false;
  }

  @Override
  protected String __str__(final Void self) {
    return "null";
  }

  @Override
  public Void implicitCast(final Scope scope, final Object o) throws CastException {
    if (o == null) {
      return null;
    }
    return super.implicitCast(scope, o);
  }

  @Override
  public Void readFromTag(final Scope scope, final CompoundTag tag) {
    return null;
  }
}
