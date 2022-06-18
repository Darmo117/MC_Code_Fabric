package net.darmo_creations.mccode.interpreter.exceptions;

import net.darmo_creations.mccode.interpreter.Scope;

/**
 * A class that reports an error that occured during execution of a program.
 *
 * @param scope    Scope of the program that threw the error.
 * @param elements An array of elements that each represent a single error from the error stack.
 */
public record ProgramErrorReport(
    Scope scope,
    ProgramErrorReportElement... elements
) {
}
