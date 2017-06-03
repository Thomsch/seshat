package ch.ceruleansands.seshat.disabled.relation;

import ch.ceruleansands.seshat.component.tile.Tile;

/**
 * The model for a relation in a java diagram.
 * @author Thomsch
 */
public class JavaRelationModel {
    private final Tile origin;
    private final Tile target;

    public JavaRelationModel(Tile origin, Tile target) {
        this.origin = origin;
        this.target = target;
    }
}
