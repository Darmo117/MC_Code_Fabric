package net.darmo_creations.mccode.interpreter.tags;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.Iterator;

/**
 * A list tag of {@link CompoundTag}s.
 */
public class CompoundTagListTag extends ListTag<CompoundTag> {
  /**
   * Creates an empty list tag.
   */
  public CompoundTagListTag() {
    super();
  }

  /**
   * Creates a list tag from the given NBT list.
   *
   * @param nbt A NBT list.
   */
  public CompoundTagListTag(final NbtList nbt) {
    super(nbt);
  }

  @Override
  public CompoundTag get(final int i) {
    return new CompoundTag(this.nbt.getCompound(i));
  }

  @Override
  public CompoundTagListTag set(final int i, final CompoundTag v) {
    this.nbt.set(i, v.toNBT());
    return this;
  }

  @Override
  public CompoundTagListTag add(final CompoundTag v) {
    this.nbt.add(v.toNBT());
    return this;
  }

  @Override
  public CompoundTagListTag add(final int i, final CompoundTag v) {
    this.nbt.add(i, v.toNBT());
    return this;
  }

  @Override
  public TagType<CompoundTag> getTagType() {
    return TagType.COMPOUND_TAG_TYPE;
  }

  @Override
  public Iterator<CompoundTag> iterator() {
    return this.nbt.stream().map(nbt -> new CompoundTag((NbtCompound) nbt)).iterator();
  }
}
