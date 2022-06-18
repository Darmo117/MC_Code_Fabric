package net.darmo_creations.mccode.interpreter;

/**
 * This class represents an element of a {@link CallStack}.
 *
 * @param moduleName Name of the module.
 * @param scopeName  Name of the sub-scope.
 * @param line       Line of the parent scope’s program this element has been created at.
 * @param column     Column of the parent scope’s program this element has been created at.
 */
public record CallStackElement(String moduleName, String scopeName, int line, int column) {
}
