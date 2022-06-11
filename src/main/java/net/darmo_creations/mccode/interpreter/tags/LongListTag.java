package net.darmo_creations.mccode.interpreter.tags;

import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtLong;

import java.util.Iterator;

/**
 * A list tag of long integers.
 */
public class LongListTag extends ListTag<Long> {
  /**
   * Creates an empty list tag.
   */
  public LongListTag() {
    super();
  }

  /**
   * Creates a list tag from the given NBT list.
   *
   * @param nbt A NBT list.
   */
  public LongListTag(final NbtList nbt) {
    super(nbt);
  }

  @Override
  public Long get(final int i) {
    return ((NbtLong) this.nbt.get(i)).longValue();
  }

  @Override
  public LongListTag set(final int i, final Long v) {
    this.nbt.set(i, NbtLong.of(v));
    return this;
  }

  @Override
  public LongListTag add(final Long v) {
    this.nbt.add(NbtLong.of(v));
    return this;
  }

  @Override
  public LongListTag add(final int i, final Long v) {
    this.nbt.add(i, NbtLong.of(v));
    return this;
  }

  @Override
  public TagType<Long> getTagType() {
    return TagType.LONG_TAG_TYPE;
  }

  @Override
  public Iterator<Long> iterator() {
    return this.nbt.stream().map(nbt -> ((NbtLong) nbt).longValue()).iterator();
  }
}
