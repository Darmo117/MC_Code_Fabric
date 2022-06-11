package net.darmo_creations.mccode.interpreter.tags;

import net.minecraft.nbt.NbtIntArray;
import net.minecraft.nbt.NbtList;

import java.util.Iterator;

/**
 * A list tag of int arrays.
 */
public class IntArrayListTag extends ListTag<int[]> {
  /**
   * Creates an empty list tag.
   */
  public IntArrayListTag() {
    super();
  }

  /**
   * Creates a list tag from the given NBT list.
   *
   * @param nbt A NBT list.
   */
  public IntArrayListTag(final NbtList nbt) {
    super(nbt);
  }

  @Override
  public int[] get(final int i) {
    return this.nbt.getIntArray(i);
  }

  @Override
  public IntArrayListTag set(final int i, final int[] v) {
    this.nbt.set(i, new NbtIntArray(v));
    return this;
  }

  @Override
  public IntArrayListTag add(final int[] v) {
    this.nbt.add(new NbtIntArray(v));
    return this;
  }

  @Override
  public IntArrayListTag add(final int i, final int[] v) {
    this.nbt.add(i, new NbtIntArray(v));
    return this;
  }

  @Override
  public TagType<int[]> getTagType() {
    return TagType.INT_ARRAY_TAG_TYPE;
  }

  @Override
  public Iterator<int[]> iterator() {
    return this.nbt.stream().map(nbt -> ((NbtIntArray) nbt).getIntArray()).iterator();
  }
}
