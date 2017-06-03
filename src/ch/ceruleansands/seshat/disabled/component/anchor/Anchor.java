package ch.ceruleansands.seshat.disabled.component.anchor;

import ch.ceruleansands.seshat.component.tile.Tile;
import javafx.beans.property.DoubleProperty;

/**
 * @author Thomsch
 */
public interface Anchor {
    void highlight(boolean highlighted);

    DoubleProperty getXProperty();

    double getX();

    DoubleProperty getYProperty();

    double getY();

    Tile getTile();
}
