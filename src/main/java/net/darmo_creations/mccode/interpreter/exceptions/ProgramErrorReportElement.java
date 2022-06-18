package net.darmo_creations.mccode.interpreter.exceptions;

import net.darmo_creations.mccode.interpreter.CallStack;

/**
 * A single element of a program report. It represents a single error with its own call stack.
 *
 * @param exception      The exception this element represents.
 * @param callStack      The program’s call stack when the error was thrown.
 * @param translationKey Error’s unlocalized translation key.
 * @param args           Report’s arguments to be used for translation of the error message.
 * @see ProgramErrorReport
 */
public record ProgramErrorReportElement(
    MCCodeException exception,
    CallStack callStack,
    String translationKey,
    Object... args
) {
}
