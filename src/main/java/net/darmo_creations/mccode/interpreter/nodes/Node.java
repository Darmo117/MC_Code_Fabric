package net.darmo_creations.mccode.interpreter.nodes;

import net.darmo_creations.mccode.interpreter.CallStack;
import net.darmo_creations.mccode.interpreter.ProgramElement;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.exceptions.EvaluationException;
import net.darmo_creations.mccode.interpreter.exceptions.MCCodeRuntimeException;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;

/**
 * A node is a component of an expression tree.
 * It returns a value when evaluated in a given scope.
 * <p>
 * Nodes can be serialized to tags.
 */
public abstract class Node extends ProgramElement {
  /**
   * Create a node.
   *
   * @param line   The line this node starts on.
   * @param column The column in the line this node starts at.
   */
  public Node(final int line, final int column) {
    super(line, column);
  }

  /**
   * Create a node from a tag.
   *
   * @param tag The tag to deserialize.
   */
  public Node(final CompoundTag tag) {
    super(tag);
  }

  /**
   * Evaluate this node.
   *
   * @param scope     The scope this node is evaluated from.
   * @param callStack The current call stack.
   * @return The value of this node.
   * @throws MCCodeRuntimeException If an error occured during evaluation.
   */
  public Object evaluate(Scope scope, CallStack callStack) throws MCCodeRuntimeException {
    return this.wrapErrors(scope, callStack, () -> this.evaluateWrapped(scope, callStack));
  }

  /**
   * Evaluate this node. Any thrown exception will be wrapped into a {@link MCCodeRuntimeException}
   * with line and column number added if missing.
   *
   * @param scope     Current scope.
   * @param callStack The current call stack.
   * @return The value of this node.
   */
  protected abstract Object evaluateWrapped(Scope scope, CallStack callStack) throws EvaluationException, ArithmeticException;
}
