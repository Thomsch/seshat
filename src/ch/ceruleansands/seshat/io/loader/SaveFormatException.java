package ch.ceruleansands.seshat.io.loader;

/**
 * Indicates a problem with the format of a saved file.
 * @author Thomsch
 */
public class SaveFormatException extends Throwable {

    private static final String elementFormat = "Expected '%s' element, found '%s' instead";

    private SaveFormatException(String message) {
        super(message);
    }

    static SaveFormatException startDiagram(String found) {
        return expectedMessage("version", found);
    }

    public static SaveFormatException startElement(String s) {
        return new SaveFormatException("Expected start of element, found " + s +" instead");
    }

    public static SaveFormatException versionNotFound(String found) {
        return expectedMessage("version", found);
    }

    public static SaveFormatException expectedMessage(String wanted, String found) {
        return new SaveFormatException(String.format(elementFormat, wanted, found));
    }

    public static SaveFormatException tileMalformed() {
        return new SaveFormatException("A tile was malformed");
    }
}
