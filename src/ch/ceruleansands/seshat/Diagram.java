package ch.ceruleansands.seshat;

import ch.ceruleansands.seshat.model.Model;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * @author Thomsch.
 */
public interface Diagram {
    Node getView();

    @Deprecated Model getModel();

    void config(Menu menuEdit, MenuItem itemSave, MenuItem itemLoad);
}
