package ch.ceruleansands.seshat.component.diagram;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * @author Thomsch
 */
public class TilePlacer implements EventHandler<MouseEvent> {

    private final IntegerProperty mouseX;
    private final IntegerProperty mouseY;

    public TilePlacer() {
        mouseX = new SimpleIntegerProperty();
        mouseY = new SimpleIntegerProperty();
    }

    @Override
    public void handle(MouseEvent event) {
        mouseX.setValue(event.getX());
        mouseY.setValue(event.getY());
    }

    double positionForX(double xTranslate) {
        return mouseX.doubleValue() + xTranslate;
    }

    double positionForY(double yTranslate) {
        return mouseY.doubleValue() + yTranslate;
    }
}
