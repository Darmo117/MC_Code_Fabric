package net.darmo_creations.mccode.interpreter.exceptions;

/**
 * A single element of a program report. It represents a single error.
 *
 * @param moduleName     Name of the module that threw the error.
 * @param line           Line where the error occured on.
 * @param column         Column of the line the error occured on.
 * @param translationKey Error’s unlocalized translation key.
 * @param args           Report’s arguments to be used for translation of the error message.
 * @see ProgramErrorReport
 */
public record ProgramErrorReportElement(
    String moduleName,
    int line,
    int column,
    String translationKey,
    Object... args
) {
}
