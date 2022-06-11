package net.darmo_creations.mccode.interpreter.tags;

import net.minecraft.nbt.NbtFloat;
import net.minecraft.nbt.NbtList;

import java.util.Iterator;

/**
 * A list tag of floats.
 */
public class FloatListTag extends ListTag<Float> {
  /**
   * Creates an empty list tag.
   */
  public FloatListTag() {
    super();
  }

  /**
   * Creates a list tag from the given NBT list.
   *
   * @param nbt A NBT list.
   */
  public FloatListTag(final NbtList nbt) {
    super(nbt);
  }

  @Override
  public Float get(final int i) {
    return this.nbt.getFloat(i);
  }

  @Override
  public FloatListTag set(final int i, final Float v) {
    this.nbt.set(i, NbtFloat.of(v));
    return this;
  }

  @Override
  public FloatListTag add(final Float v) {
    this.nbt.add(NbtFloat.of(v));
    return this;
  }

  @Override
  public FloatListTag add(final int i, final Float v) {
    this.nbt.add(i, NbtFloat.of(v));
    return this;
  }

  @Override
  public TagType<Float> getTagType() {
    return TagType.FLOAT_TAG_TYPE;
  }

  @Override
  public Iterator<Float> iterator() {
    return this.nbt.stream().map(nbt -> ((NbtFloat) nbt).floatValue()).iterator();
  }
}
