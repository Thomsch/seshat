package ch.ceruleansands.seshat.disabled.action;

import ch.ceruleansands.actionstream.Action;
import ch.ceruleansands.seshat.component.diagram.JavaDiagram;
import ch.ceruleansands.seshat.component.tile.Tile;
import ch.ceruleansands.seshat.component.tile.TileModel;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * @author Thomsch
 */
public class RevertibleNewClass implements Action {
    private final JavaDiagram diagram;
    private final Tile tile;

    /**
     * Creates a new instance of a newClassAction.
     */
    @Inject
    public RevertibleNewClass(@Assisted JavaDiagram diagram, Tile tile) {
        this.diagram = diagram;
        this.tile = tile;
    }

    @Override
    public void execute() {
        try {
            diagram.addTile(new TileModel("Unamed tile", diagram.getMousePos().getX(), diagram.getMousePos().getY()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void revert() {
        diagram.removeTile(tile);
    }
}
