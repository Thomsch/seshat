package ch.ceruleansands.seshat.loader.header;

/**
 * Contains the information contained in the header of a file.
 * @author Thomsch
 */
public class Header {
    final static String firstLine = "Seshat Project";

    final private String languageText;

    /**
     * Creates a Header container with the language contained in the file.
     * <code>languageText</code> is automatically converted to lower case.
     * @param languageText Name of the language stored in the file
     */
    Header(String languageText) {
        this.languageText = languageText.toLowerCase();
    }

    public String getLanguageText() {
        return languageText;
    }
}
