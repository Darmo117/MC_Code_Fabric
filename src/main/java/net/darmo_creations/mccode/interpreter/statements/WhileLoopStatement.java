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
public class WhileLoopStatement extends Statement {
  public static final int ID = 41;

  private static final String CONDITION_KEY = "Condition";
  private static final String STATEMENTS_KEY = "Statements";
  private static final String IP_KEY = "IP";
  private static final String PAUSED_KEY = "Paused";

  private final Node condition;
  private final List<Statement> statements;
  /**
   * Instruction pointer.
   */
  private int ip;
  /**
   * Whether the loop encountered a "wait" statement.
   */
  private boolean paused;

  /**
   * Create a statement that represents a while-loop.
   *
   * @param condition  Expression that evaluates to a boolean.
   * @param statements Statements of the loop.
   * @param line       The line this statement starts on.
   * @param column     The column in the line this statement starts at.
   */
  public WhileLoopStatement(final Node condition, final List<Statement> statements, final int line, final int column) {
    super(line, column);
    this.condition = condition;
    this.statements = statements;
    this.ip = 0;
    this.paused = false;
  }

  /**
   * Create a statement that represents a while-loop from a tag.
   *
   * @param tag The tag to deserialize.
   */
  public WhileLoopStatement(final CompoundTag tag) {
    super(tag);
    this.condition = NodeTagHelper.getNodeForTag(tag.getCompound(CONDITION_KEY));
    this.statements = StatementTagHelper.deserializeStatementsList(tag, STATEMENTS_KEY);
    this.ip = tag.getInt(IP_KEY);
    this.paused = tag.getBoolean(PAUSED_KEY);
  }

  @Override
  protected StatementAction executeWrapped(Scope scope, CallStack callStack) {
    BooleanType booleanType = ProgramManager.getTypeInstance(BooleanType.class);
    // Do not re-evaluate condition if loop was paused by "wait" a statement
    while (this.paused || booleanType.implicitCast(scope, this.condition.evaluate(scope, callStack))) {
      if (this.paused) {
        this.paused = false;
      }
      StatementAction action = this.executeStatements(scope, callStack);
      if (action == StatementAction.EXIT_LOOP) {
        break;
      } else if (action != StatementAction.PROCEED) {
        return action;
      }
      this.ip = 0;
    }
    this.ip = 0;

    return StatementAction.PROCEED;
  }

  /**
   * Executes the statements contained within the loop.
   *
   * @param scope     Current scope.
   * @param callStack The current call stack.
   * @return A {@link StatementAction}.
   */
  private StatementAction executeStatements(Scope scope, CallStack callStack) {
    while (this.ip < this.statements.size()) {
      Statement statement = this.statements.get(this.ip);
      StatementAction action = statement.execute(scope, callStack);
      if (action == StatementAction.EXIT_LOOP) {
        this.ip = 0;
        return StatementAction.EXIT_LOOP;
      } else if (action == StatementAction.CONTINUE_LOOP) {
        break;
      } else if (action == StatementAction.EXIT_FUNCTION || action == StatementAction.WAIT) {
        if (action == StatementAction.WAIT) {
          this.paused = true;
          if (statement instanceof WaitStatement) {
            this.ip++;
          }
        } else {
          this.ip = 0;
        }
        return action;
      } else {
        this.ip++;
      }
    }
    return StatementAction.PROCEED;
  }

  @Override
  public int getID() {
    return ID;
  }

  @Override
  public CompoundTag writeToTag() {
    CompoundTag tag = super.writeToTag();
    tag.putTag(CONDITION_KEY, this.condition.writeToTag());
    tag.putTag(STATEMENTS_KEY, StatementTagHelper.serializeStatementsList(this.statements));
    tag.putInt(IP_KEY, this.ip);
    tag.putBoolean(PAUSED_KEY, this.paused);
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
    WhileLoopStatement that = (WhileLoopStatement) o;
    return this.ip == that.ip && this.paused == that.paused && this.condition.equals(that.condition) && this.statements.equals(that.statements);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.condition, this.statements, this.ip, this.paused);
  }
}
