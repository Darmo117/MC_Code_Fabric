package net.darmo_creations.mccode.interpreter.exceptions;

import net.darmo_creations.mccode.interpreter.CallStack;
import net.darmo_creations.mccode.interpreter.Scope;

/**
 * An exception that is thrown when an error occurs while importing a module.
 */
public class ImportException extends MCCodeRuntimeException {
  private final CallStack callStack;
  private final MCCodeException cause;

  /**
   * Creates a new exception.
   *
   * @param scope      Current scope.
   * @param moduleName Name of the invalid module.
   * @param callStack  Call stack of the invalid module.
   * @param cause      The error thrown by the module.
   */
  public ImportException(final Scope scope, final String moduleName, CallStack callStack, final MCCodeException cause) {
    super(scope, null, "mccode.interpreter.error.import_error", moduleName);
    this.callStack = callStack;
    this.cause = cause;
  }

  @Override
  public String getName() {
    return "import_error";
  }

  /**
   * Returns the call stack of the invalid module.
   */
  public CallStack getCallStack() {
    return this.callStack;
  }

  /**
   * Returns the exception thrown by the invalid module.
   */
  @Override
  public MCCodeException getCause() {
    return this.cause;
  }
}
