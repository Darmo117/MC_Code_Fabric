package net.darmo_creations.mccode.interpreter.tags;

import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;

import java.util.Iterator;

/**
 * A list tag of strings.
 */
public class StringListTag extends ListTag<String> {
  /**
   * Creates an empty list tag.
   */
  public StringListTag() {
    super();
  }

  /**
   * Creates a list tag from the given NBT list.
   *
   * @param nbt A NBT list.
   */
  public StringListTag(final NbtList nbt) {
    super(nbt);
  }

  @Override
  public String get(final int i) {
    return this.nbt.getString(i);
  }

  @Override
  public StringListTag set(final int i, final String v) {
    this.nbt.set(i, NbtString.of(v));
    return this;
  }

  @Override
  public StringListTag add(final String v) {
    this.nbt.add(NbtString.of(v));
    return this;
  }

  @Override
  public StringListTag add(final int i, final String v) {
    this.nbt.add(i, NbtString.of(v));
    return this;
  }

  @Override
  public TagType<String> getTagType() {
    return TagType.STRING_TAG_TYPE;
  }

  @Override
  public Iterator<String> iterator() {
    return this.nbt.stream().map(NbtElement::asString).iterator();
  }
}
