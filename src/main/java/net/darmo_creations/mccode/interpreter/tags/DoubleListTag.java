package net.darmo_creations.mccode.interpreter.tags;

import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtList;

import java.util.Iterator;

/**
 * A list tag of doubles.
 */
public class DoubleListTag extends ListTag<Double> {
  /**
   * Creates an empty list tag.
   */
  public DoubleListTag() {
    super();
  }

  /**
   * Creates a list tag from the given NBT list.
   *
   * @param nbt A NBT list.
   */
  public DoubleListTag(final NbtList nbt) {
    super(nbt);
  }

  @Override
  public Double get(final int i) {
    return this.nbt.getDouble(i);
  }

  @Override
  public DoubleListTag set(final int i, final Double v) {
    this.nbt.set(i, NbtDouble.of(v));
    return this;
  }

  @Override
  public DoubleListTag add(final Double v) {
    this.nbt.add(NbtDouble.of(v));
    return this;
  }

  @Override
  public DoubleListTag add(final int i, final Double v) {
    this.nbt.add(i, NbtDouble.of(v));
    return this;
  }

  @Override
  public TagType<Double> getTagType() {
    return TagType.DOUBLE_TAG_TYPE;
  }

  @Override
  public Iterator<Double> iterator() {
    return this.nbt.stream().map(nbt -> ((NbtDouble) nbt).doubleValue()).iterator();
  }
}
