package net.darmo_creations.mccode.interpreter.nodes;

import net.darmo_creations.mccode.interpreter.CallStack;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;

import java.util.Objects;

/**
 * A node that represents a variable.
 * <p>
 * Evaluates to the value of the referenced variable.
 * May throw an exception if the variable is not declared in the given scope.
 */
public class VariableNode extends Node {
  public static final int ID = 100;

  private static final String NAME_KEY = "Name";

  private final String name;

  /**
   * Create a variable node.
   *
   * @param name   Variable’s name.
   * @param line   The line this node starts on.
   * @param column The column in the line this node starts at.
   */
  public VariableNode(final String name, final int line, final int column) {
    super(line, column);
    this.name = Objects.requireNonNull(name);
  }

  /**
   * Create a number node from a tag.
   *
   * @param tag The tag to deserialize.
   */
  public VariableNode(final CompoundTag tag) {
    super(tag);
    this.name = tag.getString(NAME_KEY);
  }

  @Override
  protected Object evaluateWrapped(final Scope scope, CallStack callStack) {
    return scope.getVariable(this.name, false);
  }

  @Override
  public CompoundTag writeToTag() {
    CompoundTag tag = super.writeToTag();
    tag.putString(NAME_KEY, this.name);
    return tag;
  }

  @Override
  public int getID() {
    return ID;
  }

  @Override
  public String toString() {
    return this.name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    VariableNode that = (VariableNode) o;
    return this.name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name);
  }
}
