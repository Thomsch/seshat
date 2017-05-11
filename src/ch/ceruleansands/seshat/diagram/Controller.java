package ch.ceruleansands.seshat.diagram;

import ch.ceruleansands.seshat.component.Anchor;
import ch.ceruleansands.seshat.gui.SelectionManager;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author Thomsch
 */
class Controller {

    private final SelectionManager selectionManager;
    private JavaDiagram diagram;
    private JavaTileModel model;
    private JavaTile tile;

    @Inject
    public Controller(SelectionManager selectionManager) {
        this.selectionManager = selectionManager;
    }

    public EventHandler<ActionEvent> newAttributeAction(JavaTileModel clazzData) {
        return event -> clazzData.addAttribute("+ new attribute");

    }

    public EventHandler<ActionEvent> newMethodAction(JavaTileModel clazzData) {
        return event -> clazzData.addMethod("+ new method");
    }

    public void onSelection(boolean isControlDown, OldTile tile) {
        if(isControlDown) {
            if(selectionManager.isTileSelected(tile)) {
                selectionManager.removeFromSelection(tile);
            } else {
                selectionManager.addToSelection(tile);
            }
        } else {
            selectionManager.changeSelection(tile);
        }
    }

    public void onNameChange(String newName) {
        model.setName(newName);
    }

    public void setModel(JavaTileModel model) {
        this.model = model;
    }

    public void startRelation(Anchor anchor) {
        diagram.startRelation(anchor);
    }

    public void endRelation(Anchor anchor) {
        diagram.endRelation(anchor);
    }

    public void setDiagram(JavaDiagram diagram) {
        this.diagram = diagram;
    }

    public JavaTile getTile() {
        return tile;
    }

    public void setTile(JavaTile tile) {
        this.tile = tile;
    }
}
