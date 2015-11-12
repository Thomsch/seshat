/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 CeruleanSands
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

package ch.ceruleansands.seshat.gui;

import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Created by Thomsch.
 */
public class Tile extends VBox{

    private final Label hello;

    private static final class DragContext {
        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }

    private DragContext dragContext;

    public Tile() {
        dragContext = new DragContext();
        setId("tile");

        setTranslateX(50);
        setTranslateY(50);

        hello = new Label("Hello");
        hello.setPrefWidth(200);

        hello.setOnMouseEntered(event -> hello.setCursor(Cursor.OPEN_HAND));

        getChildren().addAll(hello, new Separator(Orientation.HORIZONTAL), new TextArea("Attributes"), new Separator(Orientation.HORIZONTAL), new TextArea("Methods"));

        setPrefWidth(200);
        dragContext = new DragContext();

        hello.addEventFilter(MouseEvent.MOUSE_PRESSED,
            mouseEvent -> {
                    // remember initial mouse cursor coordinates
                    // and node position
                    dragContext.mouseAnchorX = mouseEvent.getScreenX();
                    dragContext.mouseAnchorY = mouseEvent.getScreenY();
                    dragContext.initialTranslateX = getTranslateX();
                    dragContext.initialTranslateY = getTranslateY();
            });

        hello.addEventFilter(MouseEvent.MOUSE_DRAGGED,
            mouseEvent -> {
                    // shift node from its initial position by delta
                    // calculated from mouse cursor movement
                setTranslateX(dragContext.initialTranslateX
                        + mouseEvent.getScreenX() - dragContext.mouseAnchorX);
                setTranslateY(dragContext.initialTranslateY
                        + mouseEvent.getScreenY() - dragContext.mouseAnchorY);
            });
    }
}
