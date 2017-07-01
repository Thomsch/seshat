package ch.thomsch.seshat.ui;

import javafx.beans.value.ChangeListener;
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

    /**
     * Creates a new instance.
     * @param tabTitle The default text for the label.
     */
    public EditableLabel(String tabTitle) {
        label = new Label(tabTitle);
        label.alignmentProperty().setValue(Pos.CENTER);

        textField = new TextField();
        replaceNode(label);

        label.setOnMouseClicked(event -> {
            if (event.getClickCount()==2) {
                textField.setText(label.getText());
                replaceNode(textField);
                textField.requestFocus();
                textField.selectAll();
                event.consume();
            }
        });

        textField.setOnAction(event -> toLabel());

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
        label.setText(textField.getText());
        replaceNode(label);
    }

    private void replaceNode(Node node) {
        getChildren().clear();
        getChildren().add(node);
    }

    public void setLabelListener(ChangeListener<String> listener) {
        label.textProperty().addListener(listener);
    }

    public void setDisplayText(String displayText) {
        label.setText(displayText);
    }
}
