package ch.ceruleansands.seshat.component.tile;

import ch.ceruleansands.seshat.component.diagram.JavaDiagram;

/**
 * @author Thomsch
 */
public interface TileFactory {
    Tile createTile(JavaDiagram javaDiagram);
}
