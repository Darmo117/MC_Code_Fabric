package net.darmo_creations.mccode.interpreter;

import net.darmo_creations.mccode.interpreter.exceptions.TypeException;

import java.lang.reflect.Field;

public final class ReflectionUtils {
  /**
   * Sets the value of a private field declared in the target class.
   *
   * @param class_     The target class.
   * @param instance   An instance of the target class.
   * @param fieldName  Name of the field to set.
   * @param fieldValue The value to set the field to.
   * @param <C>        Class’s type.
   * @param <I>        Instance’s type.
   * @throws TypeException If the specified field does not exist in the target class.
   */
  public static <C, I extends C> void setPrivateField(final Class<C> class_, I instance, final String fieldName, final Object fieldValue) {
    try {
      Field field = class_.getDeclaredField(fieldName);
      field.setAccessible(true);
      field.set(instance, fieldValue);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new TypeException("missing field '%s' for class %s".formatted(fieldName, class_.getSimpleName()));
    }
  }

  private ReflectionUtils() {
  }
}
