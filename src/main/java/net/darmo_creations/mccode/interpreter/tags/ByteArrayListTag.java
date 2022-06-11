package net.darmo_creations.mccode.interpreter.tags;

import net.minecraft.nbt.NbtByteArray;
import net.minecraft.nbt.NbtList;

import java.util.Iterator;

/**
 * A list tag of byte arrays.
 */
public class ByteArrayListTag extends ListTag<byte[]> {
  /**
   * Creates an empty list tag.
   */
  public ByteArrayListTag() {
    super();
  }

  /**
   * Creates a list tag from the given NBT list.
   *
   * @param nbt A NBT list.
   */
  public ByteArrayListTag(final NbtList nbt) {
    super(nbt);
  }

  @Override
  public byte[] get(final int i) {
    return ((NbtByteArray) this.nbt.get(i)).getByteArray();
  }

  @Override
  public ByteArrayListTag set(final int i, final byte[] v) {
    this.nbt.set(i, new NbtByteArray(v));
    return this;
  }

  @Override
  public ByteArrayListTag add(final byte[] v) {
    this.nbt.add(new NbtByteArray(v));
    return this;
  }

  @Override
  public ByteArrayListTag add(final int i, final byte[] v) {
    this.nbt.add(i, new NbtByteArray(v));
    return this;
  }

  @Override
  public TagType<byte[]> getTagType() {
    return TagType.BYTE_ARRAY_TAG_TYPE;
  }

  @Override
  public Iterator<byte[]> iterator() {
    return this.nbt.stream().map(nbt -> ((NbtByteArray) nbt).getByteArray()).iterator();
  }
}
