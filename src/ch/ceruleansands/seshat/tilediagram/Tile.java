package ch.ceruleansands.seshat.tilediagram;

import javafx.scene.Node;

/**
 * Represents a tile for the application.
 * @author Thomsch
 */
public interface Tile {

    /**
     * Returns the javafx node associated with the tile.
     * @return the javafx node
     */
    Node getNode();

    /**
     * Return the relations associated with this Tile.
     * @return the relations attached to this tile
     */
    Relation getRelation();
}
