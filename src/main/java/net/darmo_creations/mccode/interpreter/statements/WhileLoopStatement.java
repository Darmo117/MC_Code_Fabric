package net.darmo_creations.mccode.interpreter.statements;

import net.darmo_creations.mccode.interpreter.CallStack;
import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.Utils;
import net.darmo_creations.mccode.interpreter.nodes.Node;
import net.darmo_creations.mccode.interpreter.nodes.NodeTagHelper;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.darmo_creations.mccode.interpreter.type_wrappers.BooleanType;

import java.util.List;
import java.util.Objects;

/**
 * Statement that represents a while-loop.
 */
public class WhileLoopStatement extends LoopStatement {
  public static final int ID = 41;

  private static final String CONDITION_KEY = "Condition";

  private final Node condition;

  /**
   * Create a statement that represents a while-loop.
   *
   * @param condition  Expression that evaluates to a boolean.
   * @param statements Statements of the loop.
   * @param line       The line this statement starts on.
   * @param column     The column in the line this statement starts at.
   */
  public WhileLoopStatement(final Node condition, final List<Statement> statements, final int line, final int column) {
    super(statements, line, column);
    this.condition = condition;
  }

  /**
   * Create a statement that represents a while-loop from a tag.
   *
   * @param tag The tag to deserialize.
   */
  public WhileLoopStatement(final CompoundTag tag) {
    super(tag);
    this.condition = NodeTagHelper.getNodeForTag(tag.getCompound(CONDITION_KEY));
  }

  @Override
  protected StatementAction executeWrapped(Scope scope, CallStack callStack) {
    BooleanType booleanType = ProgramManager.getTypeInstance(BooleanType.class);
    // Do not re-evaluate condition if loop was paused by "wait" a statement
    while (this.paused || booleanType.implicitCast(scope, this.condition.evaluate(scope, callStack))) {
      if (this.paused) {
        this.paused = false;
      } else {
        scope.push("<while-loop>");
      }
      StatementAction action = this.executeStatements(scope, callStack);
      if (action == StatementAction.EXIT_LOOP) {
        break;
      } else if (action == StatementAction.WAIT || action == StatementAction.EXIT_FUNCTION) {
        return action;
      }
      this.ip = 0;
      scope.pop();
    }
    this.ip = 0;

    return StatementAction.PROCEED;
  }

  @Override
  protected void reset(Scope scope) {
    this.ip = 0;
    scope.pop();
  }

  @Override
  public int getID() {
    return ID;
  }

  @Override
  public CompoundTag writeToTag() {
    CompoundTag tag = super.writeToTag();
    tag.putTag(CONDITION_KEY, this.condition.writeToTag());
    return tag;
  }

  @Override
  public String toString() {
    String s = " ";
    if (!this.statements.isEmpty()) {
      s = Utils.indentStatements(this.statements);
    }
    return String.format("while %s do%send", this.condition, s);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    WhileLoopStatement that = (WhileLoopStatement) o;
    return this.condition.equals(that.condition);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), this.condition);
  }
}
