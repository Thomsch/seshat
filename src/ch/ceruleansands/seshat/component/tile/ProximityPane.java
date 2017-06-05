package ch.ceruleansands.seshat.component.tile;

import javafx.scene.layout.Pane;

/**
 * @author Thomsch
 */
public class ProximityPane extends Pane {

    public ProximityPane(Tile tile) {
        setId("proximity");

        tile.getNode().layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            setPrefSize(newValue.getWidth() * 2,newValue.getHeight() * 2);
            autosize();

            // For some reason, having a fractional position (like 200.5) in the translate causes the pane's border to not be displayed correctly.
            setTranslateX(Math.floor(tile.getNode().getTranslateX() - (getWidth() - tile.getNode().getBoundsInParent().getWidth()) / 2));
            setTranslateY(Math.floor(tile.getNode().getTranslateY() - (getHeight() - tile.getNode().getBoundsInParent().getHeight()) / 2));
        });

        tile.getNode().translateXProperty().addListener((observable, oldValue, newValue) -> {
            setTranslateX(newValue.doubleValue() - (getWidth() - tile.getNode().getBoundsInParent().getWidth()) / 2);
        });

        tile.getNode().translateYProperty().addListener((observable, oldValue, newValue) -> {
            setTranslateY(newValue.doubleValue() - (getHeight() - tile.getNode().getBoundsInParent().getHeight()) / 2);
        });
    }
}
