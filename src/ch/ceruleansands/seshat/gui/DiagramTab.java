package ch.ceruleansands.seshat.gui;

import ch.ceruleansands.seshat.Diagram;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;

import java.util.List;

/**
 * @author Thomsch.
 */
public class DiagramTab extends Tab{

    private final Diagram diagram;

    public DiagramTab(String tabTitle, Diagram diagram) {
        super(tabTitle, diagram.getView());
        this.diagram = diagram;
    }

    public EventHandler<ActionEvent> getOnSaveAction() {
        return diagram.getOnSaveAction();
    }

    public List<MenuItem> getEditItems() {
        return diagram.getEditItems();
    }
}
