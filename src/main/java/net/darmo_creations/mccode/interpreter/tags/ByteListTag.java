package net.darmo_creations.mccode.interpreter.tags;

import net.minecraft.nbt.NbtByte;
import net.minecraft.nbt.NbtList;

import java.util.Iterator;

/**
 * A list tag of bytes.
 */
public class ByteListTag extends ListTag<Byte> {
  /**
   * Creates an empty list tag.
   */
  public ByteListTag() {
    super();
  }

  /**
   * Creates a list tag from the given NBT list.
   *
   * @param nbt A NBT list.
   */
  public ByteListTag(final NbtList nbt) {
    super(nbt);
  }

  @Override
  public Byte get(final int i) {
    return ((NbtByte) this.nbt.get(i)).byteValue();
  }

  @Override
  public ByteListTag set(final int i, final Byte v) {
    this.nbt.set(i, NbtByte.of(v));
    return this;
  }

  @Override
  public ByteListTag add(final Byte v) {
    this.nbt.add(NbtByte.of(v));
    return this;
  }

  @Override
  public ByteListTag add(final int i, final Byte v) {
    this.nbt.add(i, NbtByte.of(v));
    return this;
  }

  @Override
  public TagType<Byte> getTagType() {
    return TagType.BYTE_TAG_TYPE;
  }

  @Override
  public Iterator<Byte> iterator() {
    return this.nbt.stream().map(nbt -> ((NbtByte) nbt).byteValue()).iterator();
  }
}
