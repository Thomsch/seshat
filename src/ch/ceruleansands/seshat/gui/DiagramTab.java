package ch.ceruleansands.seshat.gui;

import ch.ceruleansands.seshat.Diagram;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;

import java.util.List;

/**
 * @author Thomsch.
 */
public class DiagramTab extends Tab{

    private final Diagram diagram;

    public DiagramTab(String tabTitle, Diagram diagram) {
        super(null, diagram.getView());
        this.diagram = diagram;

        EditableLabel label = new EditableLabel(tabTitle);
        setGraphic(label);
    }

    public List<MenuItem> getEditItems() {
        return diagram.getEditItems();
    }

    public void save() {
        diagram.save();
    }
}
