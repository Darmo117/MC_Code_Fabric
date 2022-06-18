package net.darmo_creations.mccode.interpreter.exceptions;

import net.darmo_creations.mccode.interpreter.Scope;

/**
 * An exception that is thrown when an error occurs while importing a module.
 */
public class ImportException extends MCCodeRuntimeException {
  private final String moduleName;
  private final SyntaxErrorException cause;

  /**
   * Creates a new exception.
   *
   * @param scope      Current scope.
   * @param moduleName Name of the invalid module.
   * @param cause      The error thrown by the module.
   */
  public ImportException(final Scope scope, final String moduleName, final SyntaxErrorException cause) {
    super(scope, null, "mccode.interpreter.error.import_error");
    this.moduleName = moduleName;
    this.cause = cause;
  }

  /**
   * Returns the name of the invalid module.
   */
  public String getModuleName() {
    return this.moduleName;
  }

  /**
   * Returns the exception thrown by the invalid module.
   */
  @Override
  public SyntaxErrorException getCause() {
    return this.cause;
  }
}
