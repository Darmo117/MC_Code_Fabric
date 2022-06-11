package net.darmo_creations.mccode.interpreter;

import net.darmo_creations.mccode.interpreter.tags.CompoundTag;

/**
 * Objects implementing this interface indicate that
 * they can be serialized and deserialized to and from a tag.
 */
public interface TagDeserializable extends TagSerializable {
  /**
   * Restore the state of this object from the given tag.
   *
   * @param tag The tag to deserialize.
   */
  void readFromTag(final CompoundTag tag);
}
