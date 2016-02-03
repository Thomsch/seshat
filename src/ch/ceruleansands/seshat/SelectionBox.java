/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 CeruleanSands
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ch.ceruleansands.seshat;

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

        System.out.println("x = " + x + "," + y);
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
     */
    public List<Node> release(ObservableList<Node> children) {
        setVisible(false);
        return children.parallelStream().filter(node -> getLayoutBounds().contains(node.getLayoutBounds()) || getLayoutBounds().intersects(node.getLayoutBounds())).collect(Collectors.toList());
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
