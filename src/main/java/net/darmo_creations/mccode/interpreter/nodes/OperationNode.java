package net.darmo_creations.mccode.interpreter.nodes;

import net.darmo_creations.mccode.interpreter.tags.CompoundTag;

import java.util.ArrayList;
import java.util.List;

/**
 * A node that represents the call to a function or operator.
 */
public abstract class OperationNode extends Node {
  private static final String ARGUMENTS_KEY = "Arguments";

  protected final List<Node> arguments;

  /**
   * Create an operation call node.
   *
   * @param arguments Operation’s arguments.
   * @param line      The line this node starts on.
   * @param column    The column in the line this node starts at.
   */
  public OperationNode(final List<Node> arguments, final int line, final int column) {
    super(line, column);
    this.arguments = new ArrayList<>(arguments);
  }

  /**
   * Create an operation node from a tag.
   *
   * @param tag The tag to deserialize.
   */
  public OperationNode(final CompoundTag tag) {
    super(tag);
    this.arguments = NodeTagHelper.deserializeNodesList(tag, ARGUMENTS_KEY);
  }

  /**
   * Return the list of arguments.
   */
  public List<Node> getArguments() {
    return new ArrayList<>(this.arguments);
  }

  @Override
  public CompoundTag writeToTag() {
    CompoundTag tag = super.writeToTag();
    tag.putTag(ARGUMENTS_KEY, NodeTagHelper.serializeNodesList(this.arguments));
    return tag;
  }
}
