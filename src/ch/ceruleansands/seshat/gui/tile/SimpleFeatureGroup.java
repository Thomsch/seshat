package ch.ceruleansands.seshat.gui.tile;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a basic string feature for a tile.
 * The layout for a group is the following :
 * - Separator
 * - Feature 1
 * - Feature 2
 * - Ect...
 * @author Thomsch.
 * TODO : Add a feature model for more modularity and conversion feature -> node.
 */
public class SimpleFeatureGroup extends VBox{

    Map<String, Label> dirtyMap;

    public SimpleFeatureGroup() {
        dirtyMap = new HashMap<>();
    }

    public void add(String feature) {
        if(dirtyMap.isEmpty()) {
            getChildren().add(makeSeparator());
        }

        Label label = new Label(feature);
        dirtyMap.put(feature, label);
        getChildren().add(label);
    }

    public void remove(String feature) {
        Label label = dirtyMap.get(feature);
        dirtyMap.remove(feature);
        getChildren().remove(label);

        if(dirtyMap.isEmpty()) {
            getChildren().clear();
        }
    }

    private Separator makeSeparator() {
        return new Separator(Orientation.HORIZONTAL);
    }
}
