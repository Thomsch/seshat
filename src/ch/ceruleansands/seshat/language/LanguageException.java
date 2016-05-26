package ch.ceruleansands.seshat.language;

/**
 * This exception is thrown when there is a problem about the handling of a language.
 * @author Thomsch
 */
public class LanguageException extends Exception {

    private LanguageException(String message) {
        super(message);
    }

    /**
     * Creates a new exception for when a language is not loaded.
     * @param name the name of the language that is not loaded
     * @return a new instance of the exception with a message describing the problem
     */
    public static LanguageException languageNotLoaded(String name) {
        return new LanguageException("The language " + name + " is not loaded in the editor");
    }
}
