package ch.ceruleansands.seshat.disabled.io.loader;

import ch.ceruleansands.seshat.component.diagram.JavaDiagram;
import ch.ceruleansands.seshat.disabled.io.loader.header.Header;
import ch.ceruleansands.seshat.disabled.io.loader.header.HeaderException;
import ch.ceruleansands.seshat.disabled.io.loader.header.HeaderReader;
import com.google.common.io.Files;
import com.google.inject.Inject;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;

/**
 * Loads diagrams to be usable by the editor.
 * @author Thomsch.
 */
public class DiagramLoader {

    private HeaderReader headerReader;
    private final SAXLoader languageLoader;

    @Inject
    public DiagramLoader(HeaderReader headerReader, SAXLoader languageLoader) {
        this.headerReader = headerReader;
        this.languageLoader = languageLoader;
    }

    /**
     * Loads a file from the hard drive. The file is selected by the user with the help of a GUI.
     * @return The file if something is selected. An empty optional otherwise.
     */
    public Optional<File> loadFileFromDisk(Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load a diagram");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        return Optional.ofNullable(fileChooser.showOpenDialog(window));
    }

    /**
     * Load a diagram from a file.
     * The format is the following:
     * - {@link Header}
     * - Diagram content
     * @param file The file to read
     * @return The diagram ready to be used.
     * @throws FileNotFoundException when the <code>file</code> provided doesn't exist at the location
     * @throws IOException when the file cannot be closed after reading
     * @throws HeaderException when the header of the file is incorrect
     * @throws SaveFormatException when the diagram is saved in an incorrect or not recognized format
     */
    public JavaDiagram loadDiagramFromFile(File file) throws FileNotFoundException, IOException, HeaderException, SaveFormatException {
        try (BufferedReader bufferedReader = Files.newReader(file, Charset.forName("utf-8"))) {
            Header header = headerReader.read(bufferedReader);

            return languageLoader.loadFromBuffer(bufferedReader);
        }
    }
}
