package ch.ceruleansands.seshat.gui;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Line;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an empty box with dashed lines. It's handy to to select other nodes.
 * A node a selected if it's contained or intersect with the selection box.
 * This has been done instead of using a {@link javafx.scene.shape.Rectangle} which doesn't support negative coordinates.
 * @author Thomsch
 */
public class SelectionBox extends Group{

    private final SelectionLine bottom;
    private final SelectionLine top;
    private final SelectionLine left;
    private final SelectionLine right;

    public SelectionBox() {
        top = new SelectionLine();
        bottom = new SelectionLine();
        left = new SelectionLine();
        right = new SelectionLine();

        right.startXProperty().bind(top.endXProperty());
        right.startYProperty().bind(top.endYProperty());

        bottom.startXProperty().bind(left.endXProperty());
        bottom.startYProperty().bind(left.endYProperty());

        getChildren().addAll(top, bottom, left, right);
    }

    /**
     * Sets the starting point for this selection rectangle and automatically shows it.
     * @param x x coordinate
     * @param y y coordinate
     */
    public void setStart(double x, double y) {
        top.setStart(x, y);
        top.setEnd(x, y);

        left.setStart(x, y);
        left.setEnd(x, y);

        right.setEnd(x, y);
        bottom.setEnd(x, y);

        setVisible(true);
    }

    /**
     * Sets the end bound of the selection.
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void setEnd(double x, double y) {
        top.setEndX(top.getStartX() + (x - top.getStartX()));
        left.setEndY(left.getStartY() + (y - left.getStartY()));

        right.setEnd(x, y);
        bottom.setEnd(x, y);
    }

    /**
     * Return the selected nodes for the given parent and hide the selection box.
     * The current implementation is not optimal and you can notice a slow when a lot of node are passed (for me, around 100'000).
     * Considering the amount of nodes the user need to create in order to notice a slow, I'll leave it like that.
     * @param children the node eligible for selection
     * @param movingElements referential for bounds transformation
     */
    public List<Node> release(ObservableList<Node> children, Group movingElements) {
        setVisible(false);
        return children.parallelStream()
                .filter(node -> movingElements.parentToLocal(getLayoutBounds()).contains(node.getBoundsInParent()) || movingElements.parentToLocal(getLayoutBounds()).intersects(node.getBoundsInParent()))
                .collect(Collectors.toList());
    }

    /**
     * One of the side of the selection rectangle.
     */
    private class SelectionLine extends Line {
        public SelectionLine() {
            getStrokeDashArray().add(10.0);
        }

        public void setStart(double x, double y) {
            setStartX(x);
            setStartY(y);
        }

        public void setEnd(double x, double y) {
            setEndX(x);
            setEndY(y);
        }
    }
}
