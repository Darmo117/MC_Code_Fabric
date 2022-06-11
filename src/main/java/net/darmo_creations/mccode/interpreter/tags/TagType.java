package net.darmo_creations.mccode.interpreter.tags;

import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Enumeration of all NBT tag types.
 */
public final class TagType<T> {
  private static final Map<Byte, TagType<?>> TYPES = new HashMap<>();

  public static final TagType<Byte> BYTE_TAG_TYPE = register(NbtElement.BYTE_TYPE, ByteListTag::new);
  public static final TagType<Short> SHORT_TAG_TYPE = register(NbtElement.SHORT_TYPE, ShortListTag::new);
  public static final TagType<Integer> INT_TAG_TYPE = register(NbtElement.INT_TYPE, IntListTag::new);
  public static final TagType<Long> LONG_TAG_TYPE = register(NbtElement.LONG_TYPE, LongListTag::new);
  public static final TagType<Float> FLOAT_TAG_TYPE = register(NbtElement.FLOAT_TYPE, FloatListTag::new);
  public static final TagType<Double> DOUBLE_TAG_TYPE = register(NbtElement.DOUBLE_TYPE, DoubleListTag::new);
  public static final TagType<byte[]> BYTE_ARRAY_TAG_TYPE = register(NbtElement.BYTE_ARRAY_TYPE, ByteArrayListTag::new);
  public static final TagType<int[]> INT_ARRAY_TAG_TYPE = register(NbtElement.INT_ARRAY_TYPE, IntArrayListTag::new);
  public static final TagType<long[]> LONG_ARRAY_TAG_TYPE = register(NbtElement.LONG_ARRAY_TYPE, LongArrayListTag::new);
  public static final TagType<String> STRING_TAG_TYPE = register(NbtElement.STRING_TYPE, StringListTag::new);
  public static final TagType<ListTag<?>> LIST_TAG_TYPE = register(NbtElement.LIST_TYPE, ListTagListTag::new);
  public static final TagType<CompoundTag> COMPOUND_TAG_TYPE = register(NbtElement.COMPOUND_TYPE, CompoundTagListTag::new);

  private static <T> TagType<T> register(final byte id, Function<NbtList, ? extends ListTag<T>> listProvider) {
    TagType<T> tagType = new TagType<>(id, listProvider);
    TYPES.put(id, tagType);
    return tagType;
  }

  public static <T> TagType<T> fromID(final byte id) {
    //noinspection unchecked
    return (TagType<T>) TYPES.get(id);
  }

  private final byte id;
  private final Function<NbtList, ? extends ListTag<T>> listProvider;

  private TagType(final byte id, Function<NbtList, ? extends ListTag<T>> listProvider) {
    this.id = id;
    this.listProvider = listProvider;
  }

  /**
   * Returns NBT tag’s numeric ID.
   */
  public byte getID() {
    return this.id;
  }

  public <L extends ListTag<T>> L getListTag(final NbtList nbt) {
    //noinspection unchecked
    return (L) this.listProvider.apply(nbt);
  }
}
