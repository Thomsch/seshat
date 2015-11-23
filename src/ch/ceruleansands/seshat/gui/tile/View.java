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

package ch.ceruleansands.seshat.gui.tile;

import ch.ceruleansands.seshat.gui.ClazzModelView;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Thomsch
 */
class View extends VBox implements ClazzModelView {

    private final Label name;
    private final ButtonBar buttonBar;
    private final StackPane header;
    private final DragContext dragContext;

    private final Controller controller;
    private final Button newAttribute;
    private final Button newMethod;

    private final PseudoClass selectable = PseudoClass.getPseudoClass("selected");

    public View(Controller controller) {
        this.controller = controller;

        this.header = new StackPane();
        buttonBar = new ButtonBar();
        buttonBar.setButtonMinWidth(5);

        newAttribute = new Button("A");
        newMethod = new Button("M");
        newAttribute.setOnMouseEntered(useDefaultCursor(newAttribute));
        newMethod.setOnMouseEntered(useDefaultCursor(newMethod));
        buttonBar.getButtons().addAll(newAttribute, newMethod);
        dragContext = new DragContext();
        setId("tile");

        setTranslateX(50);
        setTranslateY(50);

        name = new Label("Hello");
        name.setPrefWidth(200);
        name.setAlignment(Pos.CENTER);
        name.setStyle("-fx-text-fill: #FFFFFF");

        header.setOnMouseEntered(event -> header.setCursor(Cursor.OPEN_HAND));

        header.getChildren().addAll(name, buttonBar);

        getChildren().addAll(header);

        setPrefSize(200, 200);

        header.addEventFilter(MouseEvent.MOUSE_PRESSED,
                mouseEvent -> {
                    // remember initial mouse cursor coordinates
                    // and node position
                    dragContext.mouseAnchorX = mouseEvent.getScreenX();
                    dragContext.mouseAnchorY = mouseEvent.getScreenY();
                    dragContext.initialTranslateX = getTranslateX();
                    dragContext.initialTranslateY = getTranslateY();
                });

        header.addEventFilter(MouseEvent.MOUSE_DRAGGED,
                mouseEvent -> {
                    // shift node from its initial position by delta
                    // calculated from mouse cursor movement
                    setTranslateX(dragContext.initialTranslateX
                            + mouseEvent.getScreenX() - dragContext.mouseAnchorX);
                    setTranslateY(dragContext.initialTranslateY
                            + mouseEvent.getScreenY() - dragContext.mouseAnchorY);
                });
        setFocusTraversable(true);

        addEventFilter(MouseEvent.MOUSE_CLICKED, controller::onSelection);
    }

    public void setNewAttributeButtonAction(EventHandler<ActionEvent> value) {
        newAttribute.setOnAction(value);
    }

    public void setNewMethodButtonAction(EventHandler<ActionEvent> value) {
        newMethod.setOnAction(value);
    }

    private EventHandler<? super MouseEvent> useDefaultCursor(Button button) {
        return event -> button.setCursor(Cursor.DEFAULT);
    }

    private Separator makeSeparator() {
        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.setMinSize(5,5);

        separator.setStyle("-fx-background-color: black");
        return separator;
    }

    public void setSelected(boolean selected) {
        System.out.println("selected = " + selected);
        pseudoClassStateChanged(selectable, selected);
    }

    private static final class DragContext {
        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }
}
