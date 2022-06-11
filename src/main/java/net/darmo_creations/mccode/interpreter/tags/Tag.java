package net.darmo_creations.mccode.interpreter.tags;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

public interface Tag<T extends NbtElement> {
  @Override
  String toString();

  T toNBT();

  static Tag<?> fromNBT(final NbtElement nbt) {
    if (nbt instanceof NbtCompound t) {
      return new CompoundTag(t);
    } else if (nbt instanceof NbtList t) {
      // Cannot use TagType.fromID(…).getListTag(…) because of generic types conflicts
      return switch (t.getType()) {
        case NbtElement.BYTE_TYPE -> new ByteListTag(t);
        case NbtElement.SHORT_TYPE -> new ShortListTag(t);
        case NbtElement.INT_TYPE -> new IntListTag(t);
        case NbtElement.LONG_TYPE -> new LongListTag(t);
        case NbtElement.FLOAT_TYPE -> new FloatListTag(t);
        case NbtElement.DOUBLE_TYPE -> new DoubleListTag(t);
        case NbtElement.BYTE_ARRAY_TYPE -> new ByteArrayListTag(t);
        case NbtElement.INT_ARRAY_TYPE -> new IntArrayListTag(t);
        case NbtElement.LONG_ARRAY_TYPE -> new LongArrayListTag(t);
        case NbtElement.STRING_TYPE -> new StringListTag(t);
        case NbtElement.LIST_TYPE -> new ListTagListTag(t);
        case NbtElement.COMPOUND_TYPE -> new CompoundTagListTag(t);
        default -> null;
      };
    } else {
      throw new IllegalArgumentException("NBT tag must be either NbtCompound or NbtList, %s provided"
          .formatted(nbt.getClass().getSimpleName()));
    }
  }
}
