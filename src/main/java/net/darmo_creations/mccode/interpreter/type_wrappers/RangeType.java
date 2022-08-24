package net.darmo_creations.mccode.interpreter.type_wrappers;

import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.annotations.Type;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.darmo_creations.mccode.interpreter.types.Range;

import java.util.Iterator;

/**
 * Wrapper type for {@link Range} class.
 * <p>
 * Ranges are iterable.
 */
@Type(name = RangeType.NAME,
    generateCastOperator = false,
    doc = """
        A range is an iterable object that returns values in between two bounds with a certain step.
        Ranges can be iterated over in 'for' loops and are accepted by the %len function.
        When evaluated as a `boolean, an empty range yields #false while non-empty range yield #true.""")
public class RangeType extends TypeBase<Range> {
  public static final String NAME = "range";

  private static final String START_KEY = "Start";
  private static final String END_KEY = "End";
  private static final String STEP_KEY = "Step";

  @Override
  public Class<Range> getWrappedType() {
    return Range.class;
  }

  @Override
  protected Object __eq__(final Scope scope, final Range self, final Object o) {
    if (o instanceof Range r) {
      return self.equals(r);
    }
    return super.__eq__(scope, self, o);
  }

  @Override
  protected boolean __bool__(final Range self) {
    return self.size() != 0;
  }

  @Override
  protected Iterator<?> __iter__(final Scope scope, final Range self) {
    return self.iterator();
  }

  @Override
  public Range __copy__(final Scope scope, final Range self) {
    return self.clone();
  }

  @Override
  protected long __len__(final Scope scope, final Range self) {
    return self.size();
  }

  @Override
  protected CompoundTag _writeToTag(final Range self) {
    CompoundTag tag = super._writeToTag(self);
    tag.putLong(START_KEY, self.getStart());
    tag.putLong(END_KEY, self.getEnd());
    tag.putLong(STEP_KEY, self.getStep());
    return tag;
  }

  @Override
  public Range readFromTag(final Scope scope, final CompoundTag tag) {
    return new Range(tag.getLong(START_KEY), tag.getLong(END_KEY), tag.getLong(STEP_KEY));
  }
}
