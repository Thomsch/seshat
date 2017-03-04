package ch.ceruleansands.seshat.gui;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Represents the origin of the background where the diagram is drawn.
 * @author Thomsch
 */
public class Origin extends Group {

    private Label hint;

    public Origin() {

        hint = new Label("I'm the origin");

        Circle circle = new Circle(4, Color.MAGENTA);
        hint.setTranslateX(circle.getTranslateX());
        hint.setTranslateY(circle.getTranslateY()- 25);
        hint.setVisible(false);

        circle.setOnMouseEntered(event -> hint.setVisible(true));
        circle.setOnMouseExited(event -> hint.setVisible(false));

        getChildren().addAll(circle, hint);
    }
}
