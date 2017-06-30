package ch.thomsch.seshat.component.tile;

import com.google.inject.Inject;
import javafx.scene.Node;
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
    public Tile(Controller controller) {
        model = new TileModel("Unnamed class", 0d,0d);

        view = new View(controller);
        controller.setModel(model);
        controller.setTile(this);

        model.addTileObserver(view);

        view.setNewAttributeButtonAction(controller.newAttributeAction(model));
        view.setNewMethodButtonAction(controller.newMethodAction(model));
        view.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> controller.onSelection(event.isControlDown(), this));

        view.populateFields(model.getName(), model.getAttributes(), model.getMethods());
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

    public void setSelected(boolean selected) {
        view.setSelected(selected);
    }
}
