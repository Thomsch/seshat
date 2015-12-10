package ch.ceruleansands.seshat;

import ch.ceruleansands.seshat.model.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;

import java.util.List;

/**
 * @author Thomsch.
 */
public interface Diagram {
    Node getView();

    @Deprecated Model getModel();

    EventHandler<ActionEvent> getOnSaveAction();

    List<MenuItem> getEditItems();
}
