package net.darmo_creations.mccode.interpreter.nodes;

import net.darmo_creations.mccode.interpreter.CallStack;
import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.darmo_creations.mccode.interpreter.tags.CompoundTagListTag;
import net.darmo_creations.mccode.interpreter.tags.TagType;
import net.darmo_creations.mccode.interpreter.types.MCSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A node that represents a set literal.
 */
public class SetLiteralNode extends Node {
  public static final int ID = 7;

  private static final String VALUES_KEY = "Values";

  private final List<Node> values;

  /**
   * Create a set literal node.
   *
   * @param values Set’s values.
   * @param line   The line this node starts on.
   * @param column The column in the line this node starts at.
   */
  public SetLiteralNode(final Collection<Node> values, final int line, final int column) {
    super(line, column);
    this.values = new ArrayList<>(values);
  }

  /**
   * Create a set literal node from a tag.
   *
   * @param tag The tag to deserialize.
   */
  public SetLiteralNode(final CompoundTag tag) {
    super(tag);
    CompoundTagListTag list = tag.getList(VALUES_KEY, TagType.COMPOUND_TAG_TYPE);
    this.values = new ArrayList<>();
    for (CompoundTag t : list) {
      this.values.add(NodeTagHelper.getNodeForTag(t));
    }
  }

  @Override
  protected Object evaluateWrapped(Scope scope, CallStack callStack) {
    return new MCSet(this.values.stream().map(node -> {
      Object v = node.evaluate(scope, callStack);
      return ProgramManager.getTypeForValue(v).copy(scope, v);
    }).collect(Collectors.toSet()));
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
    return this.values.stream().map(Node::toString).collect(Collectors.joining(", ", "{", "}"));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    SetLiteralNode that = (SetLiteralNode) o;
    return this.values.equals(that.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.values);
  }
}
