package net.darmo_creations.mccode.interpreter;

import net.darmo_creations.mccode.interpreter.exceptions.MCCodeRuntimeException;
import net.darmo_creations.mccode.interpreter.exceptions.MathException;
import net.darmo_creations.mccode.interpreter.exceptions.SyntaxErrorException;
import net.darmo_creations.mccode.interpreter.exceptions.WrappedException;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;

import java.util.function.Supplier;

/**
 * A program element is a component of a program’s syntax tree.
 * <p>
 * Program elements can be serialized to tags.
 */
public abstract class ProgramElement implements TagSerializable {
  /**
   * Tag key of ID property.
   */
  public static final String ID_KEY = "ElementID";
  private static final String LINE_KEY = "Line";
  private static final String COLUMN_KEY = "Column";

  private final int line;
  private final int column;

  /**
   * Create a program element.
   *
   * @param line   The line this program element starts on.
   * @param column The column in the line this program element starts at.
   */
  public ProgramElement(final int line, final int column) {
    this.line = line;
    this.column = column;
  }

  /**
   * Create a program element from a tag.
   *
   * @param tag The tag to deserialize.
   */
  public ProgramElement(final CompoundTag tag) {
    this.line = tag.getInt(LINE_KEY);
    this.column = tag.getInt(COLUMN_KEY);
  }

  /**
   * Return the line this node starts on.
   */
  public int getLine() {
    return this.line;
  }

  /**
   * Return the column in the line this node starts at.
   */
  public int getColumn() {
    return this.column;
  }

  /**
   * Return the ID of this element.
   * Used to serialize this element; must be unique to each concrete subclass.
   */
  public abstract int getID();

  @Override
  public CompoundTag writeToTag() {
    CompoundTag tag = new CompoundTag();
    tag.putInt(ID_KEY, this.getID());
    tag.putInt(LINE_KEY, this.getLine());
    tag.putInt(COLUMN_KEY, this.getColumn());
    return tag;
  }

  @Override
  public abstract String toString();

  @Override
  public abstract boolean equals(Object o);

  @Override
  public abstract int hashCode();

  /**
   * Wraps any error in a {@link MCCodeRuntimeException} or {@link SyntaxErrorException},
   * adding the line and column number of this element if missing.
   *
   * @param scope     The current scope.
   * @param callStack The current call stack.
   * @param supplier  Code to catch any error from.
   * @return A value.
   */
  protected <T> T wrapErrors(final Scope scope, CallStack callStack, final Supplier<T> supplier)
      throws MCCodeRuntimeException, SyntaxErrorException {
    CallStackElement element = new CallStackElement(scope.getProgram().getName(), scope.getTopName(), this.getLine(), this.getColumn());
    try {
      return supplier.get();
    } catch (SyntaxErrorException | WrappedException e) {
      throw e; // Explicit rethrow to not get caught by last catch clause
    } catch (MCCodeRuntimeException e) {
      if (e.getLine() == -1 || e.getColumn() == -1) {
        // Newly created MCCodeRuntimeExceptions have line and column equal to -1
        // -> set them to the correct values and add an element to the call stack
        e.setLine(this.getLine());
        e.setColumn(this.getColumn());
        callStack.push(element);
      }
      throw e;
    } catch (ArithmeticException e) {
      // Custom exception wrapper for math errors
      callStack.push(element);
      throw new MathException(scope, this.getLine(), this.getColumn(), e.getMessage());
    } catch (NullPointerException e) {
      callStack.push(element);
      // Custom exception wrapper for NPEs
      throw new WrappedException(e, this.getLine(), this.getColumn(),
          "mccode.interpreter.error.null_pointer_exception");
    } catch (Throwable e) {
      callStack.push(element);
      // Wrap any other exception to prevent them from being caught by try-except statements
      throw new WrappedException(e, this.getLine(), this.getColumn(),
          "mccode.interpreter.error.exception", e.getClass().getSimpleName(), e.getMessage());
    }
  }
}
