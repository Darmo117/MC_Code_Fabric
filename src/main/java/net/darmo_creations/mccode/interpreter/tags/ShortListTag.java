package net.darmo_creations.mccode.interpreter.tags;

import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtShort;

import java.util.Iterator;

/**
 * A list tag of short integers.
 */
public class ShortListTag extends ListTag<Short> {
  /**
   * Creates an empty list tag.
   */
  public ShortListTag() {
    super();
  }

  /**
   * Creates a list tag from the given NBT list.
   *
   * @param nbt A NBT list.
   */
  public ShortListTag(final NbtList nbt) {
    super(nbt);
  }

  @Override
  public Short get(final int i) {
    return this.nbt.getShort(i);
  }

  @Override
  public ShortListTag set(final int i, final Short v) {
    this.nbt.set(i, NbtInt.of(v));
    return this;
  }

  @Override
  public ShortListTag add(final Short v) {
    this.nbt.add(NbtInt.of(v));
    return this;
  }

  @Override
  public ShortListTag add(final int i, final Short v) {
    this.nbt.add(i, NbtInt.of(v));
    return this;
  }

  @Override
  public TagType<Short> getTagType() {
    return TagType.SHORT_TAG_TYPE;
  }

  @Override
  public Iterator<Short> iterator() {
    return this.nbt.stream().map(nbt -> ((NbtShort) nbt).shortValue()).iterator();
  }
}
