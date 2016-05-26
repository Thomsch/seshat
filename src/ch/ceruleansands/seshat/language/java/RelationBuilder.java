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

package ch.ceruleansands.seshat.language.java;

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
