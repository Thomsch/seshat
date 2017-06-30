package ch.thomsch.seshat.component.tile;

import ch.thomsch.seshat.ui.EditableLabel;
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
class View extends BorderPane implements TileObserver {

    private final EditableLabel name;
    private final DragContext dragContext;

    private final Controller controller;
    private final Button newAttribute;
    private final Button newMethod;

    private final SimpleFeatureGroup attributes;
    private final SimpleFeatureGroup methods;

    private final PseudoClass selectable = PseudoClass.getPseudoClass("selected");
    private final PseudoClass highlight = PseudoClass.getPseudoClass("highlight");

    View(Controller controller) {
        setId("tile");

        this.controller = controller;

        VBox buttonBar = new VBox();
        VBox features = new VBox();
        attributes = new SimpleFeatureGroup();
        methods = new SimpleFeatureGroup();

        newAttribute = new Button("A");
        newMethod = new Button("M");

        newMethod.setPrefWidth(28);
        newAttribute.setPrefWidth(28);
        buttonBar.getChildren().addAll(newAttribute, newMethod);

        dragContext = new DragContext();

        name = new EditableLabel();
        name.setOnMouseEntered(event -> name.setCursor(Cursor.OPEN_HAND));

        setTop(name);
        setCenter(features);
        setRight(buttonBar);

        features.getChildren().addAll(attributes, methods);

        setPrefSize(100, 100);

        setFocusTraversable(true);
        name.setNameChangeActionEvent(event -> onNameChanged());
        makeDraggable();
        makeFocusable();
    }

    private void makeFocusable() {
        setFocusTraversable(true);
        setOnMouseEntered(event -> requestFocus());
        setOnMouseClicked(event -> requestFocus());
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
                    toFront();
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

    void setNewAttributeButtonAction(EventHandler<ActionEvent> value) {
        newAttribute.setOnAction(value);
    }

    void setNewMethodButtonAction(EventHandler<ActionEvent> value) {
        newMethod.setOnAction(value);
    }

    void setSelected(boolean selected) {
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

    void populateFields(String name, Collection<String> attributes, Collection<String> methods) {
        this.name.setDisplayText(name);
        attributes.forEach(this::addAttributeLabel);
        methods.forEach(this::addMethodLabel);
    }

    private static final class DragContext {
        double mouseAnchorX;
        double mouseAnchorY;
        double initialTranslateX;
        double initialTranslateY;
    }
}
