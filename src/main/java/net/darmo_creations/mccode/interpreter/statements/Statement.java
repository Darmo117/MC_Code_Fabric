package net.darmo_creations.mccode.interpreter.statements;

import net.darmo_creations.mccode.interpreter.CallStack;
import net.darmo_creations.mccode.interpreter.ProgramElement;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.exceptions.EvaluationException;
import net.darmo_creations.mccode.interpreter.exceptions.MCCodeRuntimeException;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;

/**
 * Base class for statements.
 * A statement is an program instruction that can be executed and may alter the execution flow.
 * <p>
 * Statements can be serialized to NBT tags.
 */
public abstract class Statement extends ProgramElement {
  /**
   * Create a statement.
   *
   * @param line   The line this statement starts on.
   * @param column The column in the line this statement starts at.
   */
  public Statement(final int line, final int column) {
    super(line, column);
  }

  /**
   * Create a statement from a tag.
   *
   * @param tag The tag to deserialize.
   */
  public Statement(final CompoundTag tag) {
    super(tag);
  }

  /**
   * Execute this statement.
   *
   * @param scope     Current scope.
   * @param callStack The current call stack.
   * @return The action to take after this statement has been executed.
   * @throws MCCodeRuntimeException If an error occured during execution.
   */
  public StatementAction execute(Scope scope, CallStack callStack) throws MCCodeRuntimeException {
    return this.wrapErrors(scope, callStack, () -> this.executeWrapped(scope, callStack));
  }

  /**
   * Execute this statement. Any thrown exception will be wrapped into a {@link MCCodeRuntimeException}
   * with line and column number added if missing.
   *
   * @param scope     Current scope.
   * @param callStack The current call stack.
   * @return The action to take after this statement has been executed.
   */
  protected abstract StatementAction executeWrapped(Scope scope, CallStack callStack) throws EvaluationException, ArithmeticException;
}
