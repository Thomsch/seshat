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

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

/**
 * An label that can be edited upon double-clicking on it.
 * @author Thomsch.
 */
public class EditableLabel extends StackPane {

    private final Label label;
    private final TextField textField;
    private EventHandler<ActionEvent> onNameChange = event -> {};

    /**
     * Creates a new instance.
     * @param tabTitle The default text for the label.
     */
    public EditableLabel(String tabTitle) {
        label = new Label(tabTitle);
        label.setMinHeight(20);
        label.setMinWidth(100);
        label.alignmentProperty().setValue(Pos.CENTER);

        textField = new TextField();
        replaceNode(label);

        label.setOnMouseClicked(event -> {
            if (event.getClickCount()==2) {
                textField.setText(label.getText());
                replaceNode(textField);
                textField.selectAll();
                textField.requestFocus();
            }
        });

        textField.setOnAction(event -> {
            toLabel();
        });

        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                toLabel();
            }
        });
    }

    /**
     * Creates a new nameless instance.
     */
    public EditableLabel() {
        this(null);
    }

    private void toLabel() {
        System.out.println("label.getText() = " + label.getText());
        System.out.println("textField.getText() = " + textField.getText());
        label.setText(textField.getText());
        replaceNode(label);
        onNameChange.handle(new ActionEvent(label, label));
    }

    private void replaceNode(Node node) {
        getChildren().clear();
        getChildren().add(node);
    }

    public void setText(String text) {
        label.setText(text);

        onNameChange.handle(new ActionEvent(label, label));
        System.out.println("Name ? : " + label.getText());
    }

    public String getText() {
        return label.getText();
    }

    public void setNameChangeActionEvent(EventHandler<ActionEvent> event) {
        onNameChange = event;
    }

    public void setDisplayText(String displayText) {
        label.setText(displayText);
    }
}
