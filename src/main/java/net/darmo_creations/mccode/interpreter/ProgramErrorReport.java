package net.darmo_creations.mccode.interpreter;

/**
 * A class that reports an error that occured during execution of a program.
 *
 * @param scope          Program that throwed the error.
 * @param line           Line where the error occured on.
 * @param column         Column of the line the error occured on.
 * @param translationKey Error’s unlocalized translation key.
 * @param args           Report’s arguments to be used for translation of the error message.
 */
public record ProgramErrorReport(Scope scope, int line, int column, String translationKey, Object... args) {
}
