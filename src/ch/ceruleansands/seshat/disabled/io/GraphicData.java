package ch.ceruleansands.seshat.disabled.io;

import java.util.Objects;

/**
 * Contains the information to display a tile on the diagram.
 * @author Thomsch
 */
public class GraphicData {
    public final Double x;
    public final Double y;

    public GraphicData(Double x, Double y) {
        this.x = Objects.requireNonNull(x);
        this.y = Objects.requireNonNull(y);
    }

    @Override
    public String toString() {
        return "Graphic{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
