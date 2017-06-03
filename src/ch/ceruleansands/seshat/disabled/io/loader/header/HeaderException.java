package ch.ceruleansands.seshat.disabled.io.loader.header;

/**
 * Exception thrown when the file header is incorrect.
 * @author Thomsch
 */
public class HeaderException extends Exception{
    private HeaderException(String message) {
        super(message);
    }

    /**
     * Create a new exception with a message indicating that the first line is faulty.
     * @return A new Header Exception.
     */
    public static HeaderException seshatProjectMissing() {
        return new HeaderException("First line of the file is not " + "\"" + Header.firstLine + "\"");
    }
}
