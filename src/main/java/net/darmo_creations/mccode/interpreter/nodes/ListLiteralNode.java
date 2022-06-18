package net.darmo_creations.mccode.interpreter.nodes;

import net.darmo_creations.mccode.interpreter.CallStack;
import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.darmo_creations.mccode.interpreter.types.MCList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A node that represents an list literal.
 */
public class ListLiteralNode extends Node {
  public static final int ID = 5;

  private static final String VALUES_KEY = "Values";

  private final List<Node> values;

  /**
   * Create an list literal node.
   *
   * @param values List’s values.
   * @param line   The line this node starts on.
   * @param column The column in the line this node starts at.
   */
  public ListLiteralNode(final Collection<Node> values, final int line, final int column) {
    super(line, column);
    this.values = new ArrayList<>(values);
  }

  /**
   * Create an list literal node from a tag.
   *
   * @param tag The tag to deserialize.
   */
  public ListLiteralNode(final CompoundTag tag) {
    super(tag);
    this.values = NodeTagHelper.deserializeNodesList(tag, VALUES_KEY);
  }

  @Override
  protected Object evaluateWrapped(Scope scope, CallStack callStack) {
    return new MCList(this.values.stream().map(node -> {
      Object v = node.evaluate(scope, callStack);
      return ProgramManager.getTypeForValue(v).copy(scope, v);
    }).collect(Collectors.toList()));
  }

  @Override
  public CompoundTag writeToTag() {
    CompoundTag tag = super.writeToTag();
    tag.putTag(VALUES_KEY, NodeTagHelper.serializeNodesList(this.values));
    return tag;
  }

  @Override
  public int getID() {
    return ID;
  }

  @Override
  public String toString() {
    return this.values.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    ListLiteralNode that = (ListLiteralNode) o;
    return this.values.equals(that.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.values);
  }
}
