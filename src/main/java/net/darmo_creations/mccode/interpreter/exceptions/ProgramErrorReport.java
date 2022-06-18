package net.darmo_creations.mccode.interpreter.exceptions;

/**
 * A class that reports an error that occured during execution of a program.
 *
 * @param elements An array of elements that each represent a single error from the error stack.
 */
public record ProgramErrorReport(
    ProgramErrorReportElement... elements
) {
}
