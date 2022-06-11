package net.darmo_creations.mccode.interpreter;

import net.darmo_creations.mccode.interpreter.tags.CompoundTag;

/**
 * Objects implementing this interface indicate that they can be serialized to a tag.
 */
public interface TagSerializable {
  /**
   * Serialize the state of this object to a tag.
   *
   * @return The tag.
   */
  CompoundTag writeToTag();
}
