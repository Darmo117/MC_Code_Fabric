package net.darmo_creations.mccode.interpreter.exceptions;

/**
 * Exception thrown when loading a program but the path is invalid.
 */
public class ProgramPathException extends ProgramStatusException {
  /**
   * Create an exception.
   *
   * @param programName Program’s name.
   */
  public ProgramPathException(final String programName) {
    super("mccode.interpreter.error.invalid_program_path", programName);
  }
}
