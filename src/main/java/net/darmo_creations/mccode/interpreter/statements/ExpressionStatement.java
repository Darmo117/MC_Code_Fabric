package net.darmo_creations.mccode.interpreter.statements;

import net.darmo_creations.mccode.interpreter.CallStack;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.nodes.Node;
import net.darmo_creations.mccode.interpreter.nodes.NodeTagHelper;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;

import java.util.Objects;

/**
 * Statement that contains a single expression.
 */
public class ExpressionStatement extends Statement {
  public static final int ID = 30;

  private static final String EXPRESSION_KEY = "Expression";

  private final Node expression;

  /**
   * Create a statement that contains a single expression.
   *
   * @param expression The expression to evaluate.
   * @param line       The line this statement starts on.
   * @param column     The column in the line this statement starts at.
   */
  public ExpressionStatement(final Node expression, final int line, final int column) {
    super(line, column);
    this.expression = expression;
  }

  /**
   * Create a statement that contains a single expression from a tag.
   *
   * @param tag The tag to deserialize.
   */
  public ExpressionStatement(final CompoundTag tag) {
    super(tag);
    this.expression = NodeTagHelper.getNodeForTag(tag.getCompound(EXPRESSION_KEY));
  }

  @Override
  protected StatementAction executeWrapped(Scope scope, CallStack callStack) {
    this.expression.evaluate(scope, callStack);
    return StatementAction.PROCEED;
  }

  @Override
  public int getID() {
    return ID;
  }

  @Override
  public CompoundTag writeToTag() {
    CompoundTag tag = super.writeToTag();
    tag.putTag(EXPRESSION_KEY, this.expression.writeToTag());
    return tag;
  }

  @Override
  public String toString() {
    return this.expression + ";";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    ExpressionStatement that = (ExpressionStatement) o;
    return this.expression.equals(that.expression);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.expression);
  }
}
