package ch.ceruleansands.seshat;

import javafx.scene.Node;
import javafx.scene.control.MenuItem;

import java.util.List;

/**
 * @author Thomsch.
 */
public interface Diagram {
    Node getView();

    void save();

    List<MenuItem> getEditItems();
}
