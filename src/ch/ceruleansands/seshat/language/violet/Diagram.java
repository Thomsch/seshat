package ch.ceruleansands.seshat.language.violet;

import ch.ceruleansands.seshat.gui.ErgonomicMenuItem;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Thomsch
 */
public class Diagram implements ch.ceruleansands.seshat.component.Diagram {
    @Override
    public Node getView() {
        return new Label("Hello !");
    }

    @Override
    public List<ErgonomicMenuItem> getEditItems() {
        return Collections.emptyList();
    }

    @Override
    public Optional<File> save(Optional<File> file) {
        return Optional.empty();
    }
}
