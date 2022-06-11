package net.darmo_creations.mccode.interpreter.tags;

import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtList;

import java.util.Iterator;

/**
 * A list tag of integers.
 */
public class IntListTag extends ListTag<Integer> {
  /**
   * Creates an empty list tag.
   */
  public IntListTag() {
    super();
  }

  /**
   * Creates a list tag from the given NBT list.
   *
   * @param nbt A NBT list.
   */
  public IntListTag(final NbtList nbt) {
    super(nbt);
  }

  @Override
  public Integer get(final int i) {
    return this.nbt.getInt(i);
  }

  @Override
  public IntListTag set(final int i, final Integer v) {
    this.nbt.set(i, NbtInt.of(v));
    return this;
  }

  @Override
  public IntListTag add(final Integer v) {
    this.nbt.add(NbtInt.of(v));
    return this;
  }

  @Override
  public IntListTag add(final int i, final Integer v) {
    this.nbt.add(i, NbtInt.of(v));
    return this;
  }

  @Override
  public TagType<Integer> getTagType() {
    return TagType.INT_TAG_TYPE;
  }

  @Override
  public Iterator<Integer> iterator() {
    return this.nbt.stream().map(nbt -> ((NbtInt) nbt).intValue()).iterator();
  }
}
