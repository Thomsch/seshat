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
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.Objects;

/**
 * @author Thomsch
 */
public class RelationBuilder {
    private Line current;

    public RelationBuilder() {
        current = null;
    }


    public void start(IntegerProperty mouseX, IntegerProperty mouseY, Pane pane) {
        if(current == null) {
            current = new Line(mouseX.doubleValue(), mouseY.doubleValue(), mouseX.doubleValue(), mouseY.doubleValue());
            current.setId("relation");
            current.endXProperty().bind(mouseX);
            current.endYProperty().bind(mouseY);
            pane.getChildren().addAll(current);
            current.toFront();
        }
    }

    /**
     * Ends the construction of a relation.
     * @param pane The pane where to put the relation
     */
    public void stop(Pane pane) {
        Objects.requireNonNull(pane);
        if(current != null) {
            current.endXProperty().unbind();
            current.endYProperty().unbind();
            current = null;
        } else {
            throw new IllegalStateException("No relation currently in progress");
        }
    }

    public boolean isRelationInProgress() {
        return current != null;
    }
}
