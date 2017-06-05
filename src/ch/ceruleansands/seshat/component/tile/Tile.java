package ch.ceruleansands.seshat.component.tile;

import ch.ceruleansands.seshat.component.diagram.JavaDiagram;
import ch.ceruleansands.seshat.disabled.relation.JavaRelation;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.util.Collection;

/**
 * Represents a java tile on the diagram
 * @author Thomsch
 */
public class Tile {
    private final TileModel model;
    private final View view;

    @Inject
    public Tile(@Assisted JavaDiagram diagram, Controller controller) {
        model = new TileModel("Unnamed class", 0d,0d);

        view = new View(controller);
        controller.setModel(model);
        controller.setDiagram(diagram);
        controller.setTile(this);

        model.addTileObserver(view);

        view.setNewAttributeButtonAction(controller.newAttributeAction(model));
        view.setNewMethodButtonAction(controller.newMethodAction(model));
        view.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> controller.onSelection(event.isControlDown(), this));

        view.populateFields(model.getName(), model.getAttributes(), model.getMethods());

        installContextMenu();
    }

    public void installContextMenu() {
        Node node = view;
        ContextMenu contextMenu = new ContextMenu(new MenuItem("New relation"));
        contextMenu.setAutoHide(true);

        node.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, event -> {
            contextMenu.show(node, event.getScreenX(), event.getScreenY());
            event.consume();
        });
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> contextMenu.hide());
    }

    public String getName() {
        return model.getName();
    }

    public void setName(String name) {
        model.setName(name);
    }

    public Collection<String> getAttributes() {
        return model.getAttributes();
    }

    public Collection<String> getMethods() {
        return model.getMethods();
    }

    public Node getNode() {
        return view;
    }

    public JavaRelation getRelation() {
        return null;
    }

    public void setSelected(boolean selected) {
        view.setSelected(selected);
    }

    public void highlightAnchors(boolean highlighted) {
        view.highlight(highlighted);
    }
}
