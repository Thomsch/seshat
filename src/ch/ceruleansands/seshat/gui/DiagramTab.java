package ch.ceruleansands.seshat.gui;

import ch.ceruleansands.seshat.Diagram;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * @author Thomsch.
 */
public class DiagramTab extends Tab{

    private final Diagram diagram;
    private final EditableLabel label;
    private Optional<File> file;

    public DiagramTab(String tabTitle, Diagram diagram) {
        super(null, diagram.getView());
        this.diagram = diagram;
        this.file = Optional.empty();

        label = new EditableLabel(tabTitle);
        setGraphic(label);
    }

    public List<MenuItem> getEditItems() {
        return diagram.getEditItems();
    }

    public void save() {
        file = diagram.save(file);
        file.ifPresent(c -> label.setDisplayText(c.getName()));
    }
}
