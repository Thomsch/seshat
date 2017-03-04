package ch.ceruleansands.seshat.io;

import ch.ceruleansands.seshat.language.java.JavaTile;
import javafx.stage.Window;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * @author Thomsch.
 */
public interface Exporter {
    Optional<File> export(Optional<File> file, Window window, List<JavaTile> tiles);
}
