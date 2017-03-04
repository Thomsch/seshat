package ch.ceruleansands.seshat.language.java.gui.tile;

import ch.ceruleansands.seshat.language.java.JavaDiagram;
import ch.ceruleansands.seshat.language.java.JavaTile;
import ch.ceruleansands.seshat.language.java.JavaTileModel;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import javafx.scene.input.MouseEvent;

/**
 * Created by Thomsch.
 */
public class OldTile {

    private final JavaTileModel model;
    private Controller controller;
    private final View view;

    @Inject
    public OldTile(@Assisted JavaTileModel clazzData, @Assisted JavaDiagram diagram, @Assisted JavaTile tile, Controller controller) {
        view = new View(controller);
        model = clazzData;
        controller.setModel(clazzData);
        controller.setDiagram(diagram);
        controller.setTile(tile);

        model.addClazzObserver(view);

        view.setNewAttributeButtonAction(controller.newAttributeAction(clazzData));
        view.setNewMethodButtonAction(controller.newMethodAction(clazzData));
        view.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> controller.onSelection(event.isControlDown(), this));

        view.populateFields(model.getName(), model.getAttributes(), model.getMethods());

        this.controller = controller;
    }

    public void setSelected(boolean selected) {
        view.setSelected(selected);
    }

    public View getView() {
        return view;
    }

    public void highlightAnchors(boolean highlighted) {
        view.highlight(highlighted);
    }
}
