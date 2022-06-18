package net.darmo_creations.mccode.interpreter.statements;

import net.darmo_creations.mccode.interpreter.CallStack;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;

import java.util.List;
import java.util.Objects;

/**
 * Base class for loop statements.
 */
public abstract class LoopStatement extends Statement {
  private static final String STATEMENTS_KEY = "Statements";
  private static final String IP_KEY = "IP";
  private static final String PAUSED_KEY = "Paused";

  protected final List<Statement> statements;
  /**
   * Instruction pointer.
   */
  protected int ip;
  /**
   * Whether the loop encountered a "wait" statement.
   */
  protected boolean paused;

  public LoopStatement(final List<Statement> statements, final int line, final int column) {
    super(line, column);
    this.statements = statements;
    this.ip = 0;
    this.paused = false;
  }

  public LoopStatement(final CompoundTag tag) {
    super(tag);
    this.statements = StatementTagHelper.deserializeStatementsList(tag, STATEMENTS_KEY);
    this.ip = tag.getInt(IP_KEY);
    this.paused = tag.getBoolean(PAUSED_KEY);
  }

  /**
   * Executes the statements contained within this loop.
   *
   * @param scope     Current scope.
   * @param callStack The current call stack.
   * @return A {@link StatementAction}.
   */
  protected StatementAction executeStatements(Scope scope, CallStack callStack) {
    while (this.ip < this.statements.size()) {
      Statement statement = this.statements.get(this.ip);
      StatementAction action = statement.execute(scope, callStack);
      if (action == StatementAction.EXIT_LOOP || action == StatementAction.CONTINUE_LOOP) {
        return action;
      } else if (action == StatementAction.EXIT_FUNCTION || action == StatementAction.WAIT) {
        if (action == StatementAction.WAIT) {
          this.paused = true;
          if (statement instanceof WaitStatement) {
            this.ip++;
          }
        } else {
          this.reset(scope);
        }
        return action;
      } else {
        this.ip++;
      }
    }
    return StatementAction.PROCEED;
  }

  protected abstract void reset(Scope scope);

  @Override
  public CompoundTag writeToTag() {
    CompoundTag tag = super.writeToTag();
    tag.putTag(STATEMENTS_KEY, StatementTagHelper.serializeStatementsList(this.statements));
    tag.putInt(IP_KEY, this.ip);
    tag.putBoolean(PAUSED_KEY, this.paused);
    return tag;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    LoopStatement that = (LoopStatement) o;
    return this.ip == that.ip && this.paused == that.paused && this.statements.equals(that.statements);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.statements, this.ip, this.paused);
  }
}
