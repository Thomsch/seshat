package ch.ceruleansands.seshat.diagram;

import ch.ceruleansands.seshat.component.Anchor;
import javafx.beans.property.IntegerProperty;
import javafx.scene.Group;
import javafx.scene.shape.Line;

/**
 * Handler for the creation of a relation.
 * I'm currently not happy with the look of it because of it's heavily state oriented features.
 * @author Thomsch
 */
public class RelationBuilder {
    private Line current;
    private Anchor origin;

    public RelationBuilder() {
        current = null;
        origin = null;
    }

    public void start(Anchor origin, IntegerProperty mouseX, IntegerProperty mouseY, Group group) {
        if(current == null) {
            current = new Line();
            this.origin = origin;
            current.setId("relation");
            current.setMouseTransparent(true);
            current.startXProperty().bind(origin.getXProperty());
            current.startYProperty().bind(origin.getYProperty());
            current.endXProperty().bind(mouseX);
            current.endYProperty().bind(mouseY);
            group.getChildren().addAll(current);
        }
    }

    /**
     * Ends the construction of a relation.
     * @param end the anchor on which the relation's end will be attached to
     */
    public JavaRelationModel stop(Anchor end) {
        if(current != null) {
            current.endXProperty().bind(end.getXProperty());
            current.endYProperty().bind(end.getYProperty());

            JavaRelationModel javaRelationModel = new JavaRelationModel(origin.getTile(), end.getTile());
            resetState();
            return javaRelationModel;
        } else {
            throw new IllegalStateException("No relation currently in progress");
        }
    }

    private void resetState() {
        current = null;
        origin = null;
    }

    public boolean isRelationInProgress() {
        return current != null;
    }

    public void cancel(Group group) {
        group.getChildren().remove(current);
        resetState();
    }
}
