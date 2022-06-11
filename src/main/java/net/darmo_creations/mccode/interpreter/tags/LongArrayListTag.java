package net.darmo_creations.mccode.interpreter.tags;

import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtLongArray;

import java.util.Iterator;

/**
 * A list tag of long arrays.
 */
public class LongArrayListTag extends ListTag<long[]> {
  /**
   * Creates an empty list tag.
   */
  public LongArrayListTag() {
    super();
  }

  /**
   * Creates a list tag from the given NBT list.
   *
   * @param nbt A NBT list.
   */
  public LongArrayListTag(final NbtList nbt) {
    super(nbt);
  }

  @Override
  public long[] get(final int i) {
    return this.nbt.getLongArray(i);
  }

  @Override
  public LongArrayListTag set(final int i, final long[] v) {
    this.nbt.set(i, new NbtLongArray(v));
    return this;
  }

  @Override
  public LongArrayListTag add(final long[] v) {
    this.nbt.add(new NbtLongArray(v));
    return this;
  }

  @Override
  public LongArrayListTag add(final int i, final long[] v) {
    this.nbt.add(i, new NbtLongArray(v));
    return this;
  }

  @Override
  public TagType<long[]> getTagType() {
    return TagType.LONG_ARRAY_TAG_TYPE;
  }

  @Override
  public Iterator<long[]> iterator() {
    return this.nbt.stream().map(nbt -> ((NbtLongArray) nbt).getLongArray()).iterator();
  }
}
