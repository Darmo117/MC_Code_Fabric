package net.darmo_creations.mccode.interpreter;

import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.darmo_creations.mccode.interpreter.tags.CompoundTagListTag;
import net.darmo_creations.mccode.interpreter.tags.TagType;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The call stack represents the succession of all function calls from the module’s main scope.
 * <p>
 * Each time a user function or module method is called, an element is added, then removed once it returns.
 */
public class CallStack implements Iterable<CallStackElement>, TagDeserializable {
  private static final String ELEMENTS_KEY = "Elements";
  private static final String MODULE_NAME_KEY = "ModuleName";
  private static final String SCOPE_NAME_KEY = "ScopeName";
  private static final String LINE_KEY = "Line";
  private static final String COLUMN_KEY = "Column";

  private final List<CallStackElement> elements = new LinkedList<>();

  /**
   * Adds an element.
   *
   * @param e The element to add.
   */
  public void push(final CallStackElement e) {
    this.elements.add(e);
  }

  /**
   * Removes the last element.
   *
   * @return The removed element.
   * @throws IndexOutOfBoundsException If this stack is empty.
   */
  public CallStackElement pop() {
    return this.elements.remove(this.elements.size() - 1);
  }

  /**
   * Returns whether this stack is empty.
   *
   * @return True if this list contains no elements, false otherwise.
   */
  public boolean isEmpty() {
    return this.elements.isEmpty();
  }

  /**
   * Returns the size of this stack.
   *
   * @return The number of elements in this stack.
   */
  public int size() {
    return this.elements.size();
  }

  @Override
  public Iterator<CallStackElement> iterator() {
    return this.elements.iterator();
  }

  @Override
  public CompoundTag writeToTag() {
    CompoundTag nbt = new CompoundTag();
    CompoundTagListTag list = new CompoundTagListTag();
    for (CallStackElement element : this.elements) {
      CompoundTag tag = new CompoundTag();
      tag.putString(MODULE_NAME_KEY, element.moduleName());
      tag.putString(SCOPE_NAME_KEY, element.scopeName());
      tag.putInt(LINE_KEY, element.line());
      tag.putInt(COLUMN_KEY, element.column());
    }
    nbt.putTag(ELEMENTS_KEY, list);
    return nbt;
  }

  @Override
  public void readFromTag(CompoundTag tag) {
    this.elements.clear();
    for (CompoundTag nbt : tag.getList(ELEMENTS_KEY, TagType.COMPOUND_TAG_TYPE)) {
      this.elements.add(new CallStackElement(
          nbt.getString(MODULE_NAME_KEY),
          nbt.getString(SCOPE_NAME_KEY),
          nbt.getInt(LINE_KEY),
          nbt.getInt(COLUMN_KEY)
      ));
    }
  }
}
