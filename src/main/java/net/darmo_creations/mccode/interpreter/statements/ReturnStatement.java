package net.darmo_creations.mccode.interpreter.statements;

import net.darmo_creations.mccode.interpreter.CallStack;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.Variable;
import net.darmo_creations.mccode.interpreter.nodes.Node;
import net.darmo_creations.mccode.interpreter.nodes.NodeTagHelper;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;

import java.util.Objects;

/**
 * Statement that exits the function it is in.
 */
public class ReturnStatement extends Statement {
  public static final int ID = 62;
  public static final String RETURN_SPECIAL_VAR_NAME = "$return";

  private static final String EXPR_KEY = "Expression";

  private final Node node;

  /**
   * Create a return statement.
   *
   * @param node   Expression to return. May be null.
   * @param line   The line this statement starts on.
   * @param column The column in the line this statement starts at.
   */
  public ReturnStatement(final Node node, final int line, final int column) {
    super(line, column);
    this.node = Objects.requireNonNull(node);
  }

  /**
   * Create a return statement from a tag.
   *
   * @param tag The tag to deserialize.
   */
  public ReturnStatement(final CompoundTag tag) {
    super(tag);
    this.node = NodeTagHelper.getNodeForTag(tag.getCompound(EXPR_KEY));
  }

  @Override
  protected StatementAction executeWrapped(Scope scope, CallStack callStack) {
    Object value = this.node != null ? this.node.evaluate(scope, callStack) : null;
    scope.declareVariable(new Variable(RETURN_SPECIAL_VAR_NAME, false, false, true, false, value));
    return StatementAction.EXIT_FUNCTION;
  }

  @Override
  public CompoundTag writeToTag() {
    CompoundTag tag = super.writeToTag();
    tag.putTag(EXPR_KEY, this.node.writeToTag());
    return tag;
  }

  @Override
  public int getID() {
    return ID;
  }

  @Override
  public String toString() {
    return String.format("return %s;", this.node);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    ReturnStatement that = (ReturnStatement) o;
    return this.node.equals(that.node);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.node);
  }
}
