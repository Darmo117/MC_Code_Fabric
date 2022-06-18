package net.darmo_creations.mccode.interpreter.statements;

import net.darmo_creations.mccode.interpreter.CallStack;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;

import java.util.Objects;

/**
 * Statement that deletes a variable or function.
 */
public class DeleteVariableStatement extends Statement {
  public static final int ID = 20;

  private static final String VAR_NAME_KEY = "VariableName";

  private final String variableName;

  /**
   * Create a statement that deletes a variable or function.
   *
   * @param variableName Name of the variable or function to delete.
   * @param line         The line this statement starts on.
   * @param column       The column in the line this statement starts at.
   */
  public DeleteVariableStatement(final String variableName, final int line, final int column) {
    super(line, column);
    this.variableName = Objects.requireNonNull(variableName);
  }

  /**
   * Create a statement that deletes a variable or function from a tag.
   *
   * @param tag The tag to deserialize.
   */
  public DeleteVariableStatement(final CompoundTag tag) {
    super(tag);
    this.variableName = tag.getString(VAR_NAME_KEY);
  }

  @Override
  protected StatementAction executeWrapped(Scope scope, CallStack callStack) {
    scope.deleteVariable(this.variableName, false);
    return StatementAction.PROCEED;
  }

  @Override
  public int getID() {
    return ID;
  }

  @Override
  public CompoundTag writeToTag() {
    CompoundTag tag = super.writeToTag();
    tag.putString(VAR_NAME_KEY, this.variableName);
    return tag;
  }

  @Override
  public String toString() {
    return String.format("del %s;", this.variableName);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    DeleteVariableStatement that = (DeleteVariableStatement) o;
    return this.variableName.equals(that.variableName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.variableName);
  }
}
