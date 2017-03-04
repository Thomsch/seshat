package ch.ceruleansands.seshat.component;

import ch.ceruleansands.seshat.gui.ErgonomicMenuItem;
import javafx.scene.Node;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * @author Thomsch.
 */
public interface Diagram {
    Node getView();

    List<ErgonomicMenuItem> getEditItems();

    /**
     * Saves the diagram on the disk.
     * @return The file where the diagram has been saved to.
     * @param file The file where the diagram is already saved.
     */
    Optional<File> save(Optional<File> file);
}
