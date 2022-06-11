package net.darmo_creations.mccode.interpreter.tags;

import net.minecraft.nbt.NbtList;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class ListTag<T> implements Tag<NbtList>, Iterable<T> {
  protected final NbtList nbt;

  public ListTag() {
    this.nbt = new NbtList();
  }

  public ListTag(final NbtList nbt) {
    if (nbt.getHeldType() != 0 && nbt.getHeldType() != this.getTagType().getID()) {
      throw new IllegalArgumentException("mismatch tag types: expected %d, got %d"
          .formatted(this.getTagType().getID(), nbt.getHeldType()));
    }
    this.nbt = nbt.copy();
  }

  public abstract T get(final int i);

  public abstract ListTag<T> set(final int i, final T v);

  public abstract ListTag<T> add(final T v);

  public abstract ListTag<T> add(final int i, final T v);

  public abstract TagType<T> getTagType();

  public Stream<T> stream() {
    return StreamSupport.stream(this.spliterator(), false);
  }

  public int size() {
    return this.nbt.size();
  }

  @Override
  public NbtList toNBT() {
    return this.nbt;
  }
}
