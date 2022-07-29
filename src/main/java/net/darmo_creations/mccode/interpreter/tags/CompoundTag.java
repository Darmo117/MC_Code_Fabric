package net.darmo_creations.mccode.interpreter.tags;

import net.minecraft.nbt.NbtCompound;

import java.util.Set;

@SuppressWarnings("unused")
public class CompoundTag implements Tag<NbtCompound> {
  private final NbtCompound nbt;

  public CompoundTag() {
    this.nbt = new NbtCompound();
  }

  public CompoundTag(final NbtCompound nbt) {
    this.nbt = nbt.copy();
  }

  public boolean getBoolean(final String key) {
    return this.nbt.getBoolean(key);
  }

  public void putBoolean(final String key, final boolean b) {
    this.nbt.putBoolean(key, b);
  }

  public byte getByte(final String key) {
    return this.nbt.getByte(key);
  }

  public void putByte(final String key, final byte b) {
    this.nbt.putByte(key, b);
  }

  public short getShort(final String key) {
    return this.nbt.getShort(key);
  }

  public void putShort(final String key, final short s) {
    this.nbt.putShort(key, s);
  }

  public int getInt(final String key) {
    return this.nbt.getInt(key);
  }

  public void putInt(final String key, final int i) {
    this.nbt.putInt(key, i);
  }

  public long getLong(final String key) {
    return this.nbt.getLong(key);
  }

  public void putLong(final String key, final long l) {
    this.nbt.putLong(key, l);
  }

  public float getFloat(final String key) {
    return this.nbt.getFloat(key);
  }

  public void putFloat(final String key, final float f) {
    this.nbt.putFloat(key, f);
  }

  public double getDouble(final String key) {
    return this.nbt.getDouble(key);
  }

  public void putDouble(final String key, final double d) {
    this.nbt.putDouble(key, d);
  }

  public byte[] getByteArray(final String key) {
    return this.nbt.getByteArray(key);
  }

  public void putByteArray(final String key, final byte[] ba) {
    this.nbt.putByteArray(key, ba);
  }

  public int[] getIntArray(final String key) {
    return this.nbt.getIntArray(key);
  }

  public void putIntArray(final String key, final int[] ia) {
    this.nbt.putIntArray(key, ia);
  }

  public long[] getLongArray(final String key) {
    return this.nbt.getLongArray(key);
  }

  public void putLongArray(final String key, final long[] la) {
    this.nbt.putLongArray(key, la);
  }

  public String getString(final String key) {
    return this.nbt.getString(key);
  }

  public void putString(final String key, final String s) {
    this.nbt.putString(key, s);
  }

  public CompoundTag getCompound(final String key) {
    return new CompoundTag(this.nbt.getCompound(key));
  }

  public <T, L extends ListTag<T>> L getList(final String key, final TagType<T> type) {
    return type.getListTag(this.nbt.getList(key, type.getID()));
  }

  public void putTag(final String key, final Tag<?> tag) {
    this.nbt.put(key, tag.toNBT());
  }

  public boolean contains(final String key) {
    return this.nbt.contains(key);
  }

  public boolean contains(final String key, final TagType<?> tagType) {
    return this.nbt.contains(key, tagType.getID());
  }

  public Set<String> getKeys() {
    return this.nbt.getKeys();
  }

  @Override
  public NbtCompound toNBT() {
    return this.nbt;
  }

  @Override
  public String toString() {
    return nbt.toString();
  }
}
