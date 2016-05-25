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

package ch.ceruleansands.seshat.language.java.gui.tile;

import ch.ceruleansands.seshat.TileObserver;
import ch.ceruleansands.seshat.gui.ClazzModelView;
import ch.ceruleansands.seshat.gui.EditableLabel;
import ch.ceruleansands.seshat.language.java.Anchor;
import ch.ceruleansands.seshat.language.java.JavaTile;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.Collection;

/**
 * @author Thomsch
 */
class View extends BorderPane implements ClazzModelView, TileObserver, Anchor {

    private final EditableLabel name;
    private final DragContext dragContext;

    private final Controller controller;
    private final Button newAttribute;
    private final Button newMethod;

    private final SimpleFeatureGroup attributes;
    private final SimpleFeatureGroup methods;

    private final PseudoClass selectable = PseudoClass.getPseudoClass("selected");
    private final PseudoClass highlight = PseudoClass.getPseudoClass("highlight");
    private final VBox features;

    private final SimpleDoubleProperty anchorX;
    private final SimpleDoubleProperty anchorY;



    public View(Controller controller) {
        this.controller = controller;

        VBox buttonBar = new VBox();
        features = new VBox();
        attributes = new SimpleFeatureGroup();
        methods = new SimpleFeatureGroup();

        newAttribute = new Button("A");
        newMethod = new Button("M");

        newMethod.setPrefWidth(28);
        newAttribute.setPrefWidth(28);
        buttonBar.getChildren().addAll(newAttribute, newMethod);

        anchorX = new SimpleDoubleProperty();
        anchorY = new SimpleDoubleProperty();

        dragContext = new DragContext();
        setId("javatile");

        setLayoutX(50);
        setLayoutY(50);

        name = new EditableLabel();
//        name.setOnMouseEntered(event -> name.setCursor(Cursor.OPEN_HAND));

//        setCenter(name);
        setTop(name);
//        setCenter(features);
//        setRight(buttonBar);

        features.getChildren().addAll(attributes, methods);

        translateXProperty().addListener((observable, oldValue, newValue) -> anchorX.setValue(getAnchorXPos(getBoundsInParent().getMinX(), getBoundsInParent().getMaxX())));
        translateYProperty().addListener((observable, oldValue, newValue) -> anchorY.setValue(getAnchorYPos(getBoundsInParent().getMinY(), getBoundsInParent().getMaxY())));

        setPrefSize(100, 100);

        setFocusTraversable(true);
        name.setNameChangeActionEvent(event -> onNameChanged());
        makeDraggable();
        makeFocusable();
        relationGenerator();

        layoutBoundsProperty().addListener((observable, oldValue, newValue) -> updateAnchorPosition());
    }

    private void updateAnchorPosition() {
        anchorX.setValue(getAnchorXPos(getBoundsInParent().getMinX(), getBoundsInParent().getMaxX()));
        anchorY.setValue(getAnchorYPos(getBoundsInParent().getMinY(), getBoundsInParent().getMaxY()));
    }

    private void makeFocusable() {
        setFocusTraversable(true);
        setOnMouseEntered(event -> requestFocus());
        setOnMouseClicked(event -> requestFocus());
    }

    private void relationGenerator() {
        addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode() == KeyCode.R) {
                controller.startRelation(this);
            }
        });

        addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            controller.endRelation(this);
        });
    }

    private void makeDraggable() {
        addEventHandler(MouseEvent.MOUSE_PRESSED,
                mouseEvent -> {
                    // remember initial mouse cursor coordinates
                    // and node position
                    dragContext.mouseAnchorX = mouseEvent.getScreenX();
                    dragContext.mouseAnchorY = mouseEvent.getScreenY();
                    dragContext.initialTranslateX = getTranslateX();
                    dragContext.initialTranslateY = getTranslateY();
                    mouseEvent.consume();
                });

        addEventHandler(MouseEvent.MOUSE_DRAGGED,
                mouseEvent -> {
                    // shift node from its initial position by delta
                    // calculated from mouse cursor movement
                    setTranslateX(dragContext.initialTranslateX
                            + mouseEvent.getScreenX() - dragContext.mouseAnchorX);
                    setTranslateY(dragContext.initialTranslateY
                            + mouseEvent.getScreenY() - dragContext.mouseAnchorY);

                    mouseEvent.consume();
                });
    }

    private void onNameChanged() {
        controller.onNameChange(name.getText());
    }

    public void setNewAttributeButtonAction(EventHandler<ActionEvent> value) {
        newAttribute.setOnAction(value);
    }

    public void setNewMethodButtonAction(EventHandler<ActionEvent> value) {
        newMethod.setOnAction(value);
    }

    public void setSelected(boolean selected) {
        pseudoClassStateChanged(selectable, selected);
    }

    private void setHighlighted(boolean highlighted) {
        pseudoClassStateChanged(highlight, highlighted);
    }

    @Override
    public void onNameChanged(String oldName, String newName) {
        name.setDisplayText(newName);
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

    public void populateFields(String name, Collection<String> attributes, Collection<String> methods) {
        this.name.setDisplayText(name);
        attributes.forEach(this::addAttributeLabel);
        methods.forEach(this::addMethodLabel);
    }

    @Override
    public double getX() {
        return getAnchorXPos(getBoundsInParent().getMinX(), getBoundsInParent().getMaxX());
    }


    @Override
    public double getY() {
        return getAnchorYPos(getBoundsInParent().getMinY(), getBoundsInParent().getMaxY());
    }

    @Override
    public JavaTile getTile() {
        return controller.getTile();
    }

    @Override
    public void highlight(boolean highlighted) {
        setHighlighted(highlighted);
    }

    @Override
    public DoubleProperty getXProperty() {
        return anchorX;
    }

    @Override
    public DoubleProperty getYProperty() {
        return anchorY;
    }

    private static final class DragContext {
        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }

    private double getAnchorXPos(double min, double max) {
        return (max - min) / 2 + min;
    }

    private double getAnchorYPos(double min, double max) {
        return (max - min) * 2 / 3 + min;
    }
}
