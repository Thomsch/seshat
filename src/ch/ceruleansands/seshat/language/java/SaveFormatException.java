package ch.ceruleansands.seshat.language.java;

/**
 * Indicates a problem with the format of a saved file.
 * @author Thomsch
 */
public class SaveFormatException extends Throwable {
    private SaveFormatException(String message) {
        super(message);
    }

    static SaveFormatException startDocument() {
        return new SaveFormatException("The start of the document is not found");
    }

    public static SaveFormatException startElement(String s) {
        return new SaveFormatException("Expected start of element, found " + s +" instead");
    }
}
