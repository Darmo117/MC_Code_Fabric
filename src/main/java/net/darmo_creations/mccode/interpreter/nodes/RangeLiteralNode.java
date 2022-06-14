package net.darmo_creations.mccode.interpreter.nodes;

import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.exceptions.EvaluationException;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.darmo_creations.mccode.interpreter.type_wrappers.IntType;
import net.darmo_creations.mccode.interpreter.types.Range;

import java.util.Objects;

/**
 * A node that represents a range literal.
 */
public class RangeLiteralNode extends Node {
  public static final int ID = 8;

  private static final String START_KEY = "Start";
  private static final String END_KEY = "End";
  private static final String STEP_KEY = "Step";

  private final Node start;
  private final Node end;
  private final Node step;

  /**
   * Creates a range literal node with a step of 1.
   *
   * @param start  Range’s start value (included).
   * @param end    Range’s end value (excluded).
   * @param line   The line this node starts on.
   * @param column The column in the line this node starts at.
   */
  public RangeLiteralNode(final Node start, final Node end, final int line, final int column) {
    super(line, column);
    this.start = start;
    this.end = end;
    this.step = null;
  }

  /**
   * Creates a range literal node with a step of 1.
   *
   * @param start  Range’s start value (included).
   * @param end    Range’s end value (excluded).
   * @param step   Range’s step; cannot be 0.
   * @param line   The line this node starts on.
   * @param column The column in the line this node starts at.
   */
  public RangeLiteralNode(final Node start, final Node end, final Node step, final int line, final int column) {
    super(line, column);
    this.start = start;
    this.end = end;
    this.step = step;
  }

  /**
   * Create a range literal node from a tag.
   *
   * @param tag The tag to deserialize.
   */
  public RangeLiteralNode(final CompoundTag tag) {
    super(tag);
    this.start = NodeTagHelper.getNodeForTag(tag.getCompound(START_KEY));
    this.end = NodeTagHelper.getNodeForTag(tag.getCompound(END_KEY));
    this.step = NodeTagHelper.getNodeForTag(tag.getCompound(STEP_KEY));
  }

  @Override
  public int getID() {
    return ID;
  }

  @Override
  public String toString() {
    return "%s:%s:%s".formatted(this.start, this.end, this.step);
  }

  @Override
  protected Object evaluateWrapped(Scope scope) throws EvaluationException, ArithmeticException {
    IntType intType = ProgramManager.getTypeInstance(IntType.class);
    if (this.step != null) {
      return new Range(
          intType.implicitCast(scope, this.start.evaluate(scope)),
          intType.implicitCast(scope, this.end.evaluate(scope)),
          intType.implicitCast(scope, this.step.evaluate(scope))
      );
    }
    return new Range(
        intType.implicitCast(scope, this.start.evaluate(scope)),
        intType.implicitCast(scope, this.end.evaluate(scope)),
        1
    );
  }

  @Override
  public CompoundTag writeToTag() {
    CompoundTag nbt = super.writeToTag();
    nbt.putTag(START_KEY, this.start.writeToTag());
    nbt.putTag(END_KEY, this.end.writeToTag());
    nbt.putTag(STEP_KEY, this.step.writeToTag());
    return nbt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    RangeLiteralNode that = (RangeLiteralNode) o;
    return this.start == that.start && this.end == that.end && this.step == that.step;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.start, this.end, this.step);
  }
}
