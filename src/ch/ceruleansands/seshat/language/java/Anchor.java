package ch.ceruleansands.seshat.language.java;

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

    JavaTile getTile();
}
