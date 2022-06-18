package net.darmo_creations.mccode.interpreter.statements;

import net.darmo_creations.mccode.interpreter.*;
import net.darmo_creations.mccode.interpreter.nodes.Node;
import net.darmo_creations.mccode.interpreter.nodes.NodeTagHelper;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.darmo_creations.mccode.interpreter.type_wrappers.TypeBase;
import net.darmo_creations.mccode.interpreter.type_wrappers.UnaryOperator;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Statement that represents a for-loop.
 */
public class ForLoopStatement extends Statement {
  public static final int ID = 42;

  private static final String VARIABLE_NAME_KEY = "VariableName";
  private static final String VALUES_KEY = "Values";
  private static final String STATEMENTS_KEY = "Statements";
  private static final String IP_KEY = "IP";
  private static final String ITERATOR_INDEX_KEY = "IteratorIndex";
  private static final String PAUSED_KEY = "Paused";

  private final String variableName;
  private final Node values;
  private final List<Statement> statements;
  /**
   * Instruction pointer.
   */
  private int ip;
  /**
   * Index of the current value from the iterator.
   */
  private int iteratorIndex;
  /**
   * Whether the for-loop was just reloaded from an NBT tag.
   */
  private boolean resumeAfterLoad;
  /**
   * Whether the loop encountered a "wait" statement.
   */
  private boolean paused;

  /**
   * Create a statement that represents a for-loop.
   *
   * @param variableName Name of the loop variable.
   * @param values       Expression that returns an iterator.
   * @param statements   Statements of the loop.
   * @param line         The line this statement starts on.
   * @param column       The column in the line this statement starts at.
   */
  public ForLoopStatement(final String variableName, final Node values, final List<Statement> statements,
                          final int line, final int column) {
    super(line, column);
    this.variableName = variableName;
    this.values = values;
    this.statements = statements;
    this.ip = 0;
    this.iteratorIndex = 0;
    this.paused = false;
  }

  /**
   * Create a statement that represents a for-loop from a tag.
   *
   * @param tag The tag to deserialize.
   */
  public ForLoopStatement(final CompoundTag tag) {
    super(tag);
    this.variableName = tag.getString(VARIABLE_NAME_KEY);
    this.values = NodeTagHelper.getNodeForTag(tag.getCompound(VALUES_KEY));
    this.statements = StatementTagHelper.deserializeStatementsList(tag, STATEMENTS_KEY);
    this.ip = tag.getInt(IP_KEY);
    this.iteratorIndex = tag.getInt(ITERATOR_INDEX_KEY);
    this.paused = tag.getBoolean(PAUSED_KEY);
    this.resumeAfterLoad = true;
  }

  @Override
  protected StatementAction executeWrapped(Scope scope, CallStack callStack) {
    Object valuesObject = this.values.evaluate(scope, callStack);
    TypeBase<?> type = ProgramManager.getTypeForValue(valuesObject);
    Iterator<?> iterator = (Iterator<?>) type.applyOperator(scope, UnaryOperator.ITERATE, valuesObject, null, null, false);

    // Skip elements already iterated over
    for (int i = 0; i < this.iteratorIndex; i++) {
      iterator.next();
    }

    // Do not test again if loop was paused by a "wait" statement
    while (this.paused || this.resumeAfterLoad || iterator.hasNext()) {
      // If first statement returns WAIT, "ip" is not yet updated -> do not recreate variable
      if (this.ip == 0 && !scope.isVariableDefined(this.variableName)) {
        // Variable is deleted and recreated on each iteration
        scope.declareVariable(new Variable(this.variableName, false, false, false, true, iterator.next()));
        this.iteratorIndex++;
      }
      this.paused = false;
      this.resumeAfterLoad = false;

      StatementAction action = this.executeStatements(scope, callStack);
      if (action == StatementAction.EXIT_LOOP) {
        break;
      } else if (action != StatementAction.PROCEED) {
        return action;
      }
      if (scope.isVariableDefined(this.variableName)) {
        scope.deleteVariable(this.variableName, false);
      }
      this.ip = 0;
    }
    this.reset(scope);

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
          this.reset(scope);
        }
        return action;
      } else {
        this.ip++;
      }
    }
    return StatementAction.PROCEED;
  }

  private void reset(Scope scope) {
    this.ip = 0;
    this.iteratorIndex = 0;
    if (scope.isVariableDefined(this.variableName)) {
      scope.deleteVariable(this.variableName, false);
    }
  }

  @Override
  public int getID() {
    return ID;
  }

  @Override
  public CompoundTag writeToTag() {
    CompoundTag tag = super.writeToTag();
    tag.putString(VARIABLE_NAME_KEY, this.variableName);
    tag.putTag(VALUES_KEY, this.values.writeToTag());
    tag.putTag(STATEMENTS_KEY, StatementTagHelper.serializeStatementsList(this.statements));
    tag.putInt(IP_KEY, this.ip);
    tag.putInt(ITERATOR_INDEX_KEY, this.iteratorIndex);
    tag.putBoolean(PAUSED_KEY, this.paused);
    return tag;
  }

  @Override
  public String toString() {
    String s = " ";
    if (!this.statements.isEmpty()) {
      s = Utils.indentStatements(this.statements);
    }
    return String.format("for %s in %s do%send", this.variableName, this.values, s);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    ForLoopStatement that = (ForLoopStatement) o;
    return this.ip == that.ip && this.iteratorIndex == that.iteratorIndex && this.resumeAfterLoad == that.resumeAfterLoad
        && this.paused == that.paused && this.variableName.equals(that.variableName) && this.values.equals(that.values)
        && this.statements.equals(that.statements);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.variableName, this.values, this.statements, this.ip, this.iteratorIndex, this.resumeAfterLoad, this.paused);
  }
}
