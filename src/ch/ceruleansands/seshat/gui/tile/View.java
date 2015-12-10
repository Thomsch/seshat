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

import ch.ceruleansands.seshat.ClazzObserver;
import ch.ceruleansands.seshat.gui.ClazzModelView;
import ch.ceruleansands.seshat.gui.EditableLabel;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.Collection;

/**
 * @author Thomsch
 */
class View extends BorderPane implements ClazzModelView, ClazzObserver {

    private final EditableLabel name;
    private final VBox buttonBar;
    private final DragContext dragContext;

    private final Controller controller;
    private final Button newAttribute;
    private final Button newMethod;

    private final SimpleFeatureGroup attributes;
    private final SimpleFeatureGroup methods;

    private final PseudoClass selectable = PseudoClass.getPseudoClass("selected");
    private final VBox features;

    public View(Controller controller) {
        this.controller = controller;

        buttonBar = new VBox();
        features = new VBox();
        attributes = new SimpleFeatureGroup();
        methods = new SimpleFeatureGroup();

        newAttribute = new Button("A");
        newMethod = new Button("M");

        newMethod.setPrefWidth(28);
        newAttribute.setPrefWidth(28);
        newAttribute.setOnMouseEntered(useDefaultCursor(newAttribute));
        newMethod.setOnMouseEntered(useDefaultCursor(newMethod));
        buttonBar.getChildren().addAll(newAttribute, newMethod);

        dragContext = new DragContext();
        setId("tile");

        setTranslateX(50);
        setTranslateY(50);

        name = new EditableLabel();
        name.setOnMouseEntered(event -> name.setCursor(Cursor.OPEN_HAND));

        setTop(name);
        setCenter(features);
        setRight(buttonBar);

        features.getChildren().addAll(attributes, methods);

        setPrefSize(200, 200);

        addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            requestFocus();
        });

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
        name.requestFocus();
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

    public void setSelected(boolean selected) {
        pseudoClassStateChanged(selectable, selected);
    }

    @Override
    public void onNameChanged(String oldName, String newName) {
    }

    @Override
    public void onNewAttribute(String attribute) {
        addAttributeLabel(attribute);
    }

    private void addAttributeLabel(String attribute) {
        attributes.add(attribute);
    }

    @Override
    public void onNewMethod(String method) {
        addMethodLabel(method);
    }

    private void addMethodLabel(String method) {
        methods.add(method);
    }

    public String getName() {
        return name.getText();
    }

    public void setNameChangeAction(EventHandler<ActionEvent> event) {
        name.setNameChangeActionEvent(event);
    }

    public void populateFields(String name, Collection<String> attributes, Collection<String> methods) {
        this.name.setDisplayText(name);
        attributes.forEach(this::addAttributeLabel);
        methods.forEach(this::addMethodLabel);
    }

    private static final class DragContext {
        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }
}
