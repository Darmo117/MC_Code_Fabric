package net.darmo_creations.mccode.interpreter.tags;

import net.minecraft.nbt.NbtList;

import java.util.Iterator;

/**
 * A list tag of {@link ListTag}s.
 */
public class ListTagListTag extends ListTag<ListTag<?>> {
  /**
   * Creates an empty list tag.
   */
  public ListTagListTag() {
    super();
  }

  /**
   * Creates a list tag from the given NBT list.
   *
   * @param nbt A NBT list.
   */
  public ListTagListTag(final NbtList nbt) {
    super(nbt);
  }

  @Override
  public ListTag<?> get(final int i) {
    NbtList list = this.nbt.getList(i);
    return TagType.fromID(list.getHeldType()).getListTag(list);
  }

  @Override
  public ListTagListTag set(final int i, final ListTag<?> v) {
    this.nbt.set(i, v.toNBT());
    return this;
  }

  @Override
  public ListTagListTag add(final ListTag<?> v) {
    this.nbt.add(v.toNBT());
    return this;
  }

  @Override
  public ListTagListTag add(final int i, final ListTag<?> v) {
    this.nbt.add(i, v.toNBT());
    return this;
  }

  @Override
  public TagType<ListTag<?>> getTagType() {
    return TagType.LIST_TAG_TYPE;
  }

  @Override
  public Iterator<ListTag<?>> iterator() {
    return this.nbt.stream().<ListTag<?>>map(nbt -> {
      NbtList list = (NbtList) nbt;
      byte heldType = list.getHeldType();
      if (heldType == 0) {
        return new EmptyList();
      }
      return TagType.fromID(heldType).getListTag(list);
    }).iterator();
  }

  private static class EmptyList extends ListTag<Void> {
    @Override
    public Void get(int i) {
      throw new UnsupportedOperationException();
    }

    @Override
    public ListTag<Void> set(int i, Void v) {
      throw new UnsupportedOperationException();
    }

    @Override
    public ListTag<Void> add(Void v) {
      throw new UnsupportedOperationException();
    }

    @Override
    public ListTag<Void> add(int i, Void v) {
      throw new UnsupportedOperationException();
    }

    @Override
    public TagType<Void> getTagType() {
      throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<Void> iterator() {
      return new Iterator<>() {
        @Override
        public boolean hasNext() {
          return false;
        }

        @Override
        public Void next() {
          return null;
        }
      };
    }
  }
}
