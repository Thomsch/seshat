package ch.ceruleansands.seshat.language.java.action;

import ch.ceruleansands.actionstream.Action;
import ch.ceruleansands.seshat.TileIdGenerator;
import ch.ceruleansands.seshat.language.java.JavaDiagram;
import ch.ceruleansands.seshat.language.java.JavaTile;
import ch.ceruleansands.seshat.language.java.JavaTileModel;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * @author Thomsch
 */
public class RevertibleNewClass implements Action {
    private final JavaDiagram diagram;
    private final JavaTile tile;
    private final TileIdGenerator tileIdGenerator;

    /**
     * Creates a new instance of a newClassAction.
     */
    @Inject
    public RevertibleNewClass(@Assisted JavaDiagram diagram, JavaTile tile, TileIdGenerator tileIdGenerator) {
        this.diagram = diagram;
        this.tile = tile;
        this.tileIdGenerator = tileIdGenerator;
    }

    @Override
    public void execute() {
        try {
            diagram.addTile(new JavaTileModel(tileIdGenerator.getNext(), "Unamed tile", diagram.getMousePos().getX(), diagram.getMousePos().getY()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void revert() {
        diagram.removeTile(tile);
    }
}
