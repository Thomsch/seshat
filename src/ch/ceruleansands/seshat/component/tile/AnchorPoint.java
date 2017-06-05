package ch.ceruleansands.seshat.component.tile;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * @author Thomsch
 */
class AnchorPoint extends StackPane {

    public AnchorPoint() {
        Circle circle = new Circle(5);
        circle.setId("anchorpoint");

        Circle circle1 = new Circle(8, Color.AQUA);
        getChildren().addAll(circle1, circle);
    }
}
