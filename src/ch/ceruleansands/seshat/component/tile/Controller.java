package ch.ceruleansands.seshat.component.tile;

import ch.ceruleansands.seshat.component.diagram.JavaDiagram;
import ch.ceruleansands.seshat.disabled.component.anchor.Anchor;
import ch.ceruleansands.seshat.ui.SelectionManager;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author Thomsch
 */
class Controller {

    private final SelectionManager selectionManager;
    private JavaDiagram diagram;
    private TileModel model;
    private Tile tile;

    @Inject
    public Controller(SelectionManager selectionManager) {
        this.selectionManager = selectionManager;
    }

    public EventHandler<ActionEvent> newAttributeAction(TileModel clazzData) {
        return event -> clazzData.addAttribute("+ new attribute");

    }

    public EventHandler<ActionEvent> newMethodAction(TileModel clazzData) {
        return event -> clazzData.addMethod("+ new method");
    }

    public void onSelection(boolean isControlDown, Tile tile) {
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

    public void setModel(TileModel model) {
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

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }
}
