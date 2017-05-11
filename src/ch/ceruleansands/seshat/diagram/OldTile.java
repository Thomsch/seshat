package ch.ceruleansands.seshat.diagram;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import javafx.scene.input.MouseEvent;

/**
 * Created by Thomsch.
 */
public class OldTile {

    private final View view;

    @Inject
    public OldTile(@Assisted JavaTileModel clazzData, @Assisted JavaDiagram diagram, @Assisted JavaTile tile, Controller controller) {
        view = new View(controller);
        controller.setModel(clazzData);
        controller.setDiagram(diagram);
        controller.setTile(tile);

        clazzData.addClazzObserver(view);

        view.setNewAttributeButtonAction(controller.newAttributeAction(clazzData));
        view.setNewMethodButtonAction(controller.newMethodAction(clazzData));
        view.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> controller.onSelection(event.isControlDown(), this));

        view.populateFields(clazzData.getName(), clazzData.getAttributes(), clazzData.getMethods());
    }

    public void setSelected(boolean selected) {
        view.setSelected(selected);
    }

    View getView() {
        return view;
    }

    void highlightAnchors(boolean highlighted) {
        view.highlight(highlighted);
    }
}
