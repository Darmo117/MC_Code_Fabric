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
public class ForLoopStatement extends LoopStatement {
  public static final int ID = 42;

  private static final String VARIABLE_NAME_KEY = "VariableName";
  private static final String VALUES_KEY = "Values";
  private static final String ITERATOR_INDEX_KEY = "IteratorIndex";

  private final String variableName;
  private final Node values;
  /**
   * Index of the current value from the iterator.
   */
  private int iteratorIndex;
  /**
   * Whether the for-loop was just reloaded from an NBT tag.
   */
  private boolean resumeAfterLoad;

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
    super(statements, line, column);
    this.variableName = variableName;
    this.values = values;
    this.iteratorIndex = 0;
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
    this.iteratorIndex = tag.getInt(ITERATOR_INDEX_KEY);
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
      if (!this.paused && this.ip == 0) {
        scope.push("<for-loop>");
        // Variable is deleted and recreated on each iteration
        scope.declareVariable(new Variable(this.variableName, false, false, false, true, iterator.next()));
        this.iteratorIndex++;
      }
      this.paused = false;
      this.resumeAfterLoad = false;

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
    this.iteratorIndex = 0;

    return StatementAction.PROCEED;
  }

  @Override
  protected void reset(Scope scope) {
    this.ip = 0;
    this.iteratorIndex = 0;
    scope.pop();
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
    tag.putInt(ITERATOR_INDEX_KEY, this.iteratorIndex);
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
    if (!super.equals(o)) {
      return false;
    }
    ForLoopStatement that = (ForLoopStatement) o;
    return this.iteratorIndex == that.iteratorIndex && this.resumeAfterLoad == that.resumeAfterLoad && this.variableName.equals(that.variableName) && this.values.equals(that.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), this.variableName, this.values, this.iteratorIndex, this.resumeAfterLoad);
  }
}
