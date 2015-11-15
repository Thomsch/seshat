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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
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

    private final Label name;
    private final TextArea attributes;
    private final TextArea methods;

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

        name = new Label("Hello");
        name.setPrefWidth(200);
        name.setAlignment(Pos.CENTER);
        name.setStyle("-fx-text-fill: #FFFFFF");

        name.setOnMouseEntered(event -> name.setCursor(Cursor.OPEN_HAND));

        attributes = new TextArea();
        methods = new TextArea();

        getChildren().addAll(name);

        setPrefSize(200, 200);
        dragContext = new DragContext();

        name.addEventFilter(MouseEvent.MOUSE_PRESSED,
            mouseEvent -> {
                    // remember initial mouse cursor coordinates
                    // and node position
                    dragContext.mouseAnchorX = mouseEvent.getScreenX();
                    dragContext.mouseAnchorY = mouseEvent.getScreenY();
                    dragContext.initialTranslateX = getTranslateX();
                    dragContext.initialTranslateY = getTranslateY();
            });

        name.addEventFilter(MouseEvent.MOUSE_DRAGGED,
            mouseEvent -> {
                    // shift node from its initial position by delta
                    // calculated from mouse cursor movement
                setTranslateX(dragContext.initialTranslateX
                        + mouseEvent.getScreenX() - dragContext.mouseAnchorX);
                setTranslateY(dragContext.initialTranslateY
                        + mouseEvent.getScreenY() - dragContext.mouseAnchorY);
            });
        setFocusTraversable(true);
        setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent mouseEvent)
            {
                requestFocus();
            }
        });
//        addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
//            setFocused(true);
//        });

        focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    System.out.println("focus");
                } else {
                    System.out.println("nofocus");
                }
            }
        });
    }

    private Separator makeSeparator() {
        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.setMinSize(5,5);

        separator.setStyle("-fx-background-color: black");
        return separator;
    }
}
