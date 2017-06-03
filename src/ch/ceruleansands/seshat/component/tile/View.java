package ch.ceruleansands.seshat.component.tile;

import ch.ceruleansands.seshat.disabled.component.anchor.Anchor;
import ch.ceruleansands.seshat.ui.EditableLabel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Collection;

/**
 * @author Thomsch
 */
class View extends BorderPane implements TileObserver, Anchor {

    private final EditableLabel name;
    private final DragContext dragContext;

    private final Controller controller;
    private final Button newAttribute;
    private final Button newMethod;

    private final SimpleFeatureGroup attributes;
    private final SimpleFeatureGroup methods;

    private final PseudoClass selectable = PseudoClass.getPseudoClass("selected");
    private final PseudoClass highlight = PseudoClass.getPseudoClass("highlight");

    private final SimpleDoubleProperty anchorX;
    private final SimpleDoubleProperty anchorY;

    View(Controller controller) {
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

        anchorX = new SimpleDoubleProperty();
        anchorY = new SimpleDoubleProperty();

        dragContext = new DragContext();
        setId("javatile");

        setLayoutX(50);
        setLayoutY(50);

        name = new EditableLabel();
        name.setOnMouseEntered(event -> name.setCursor(Cursor.OPEN_HAND));
        name.setId("tile");

//        setCenter(name);
        setTop(name);
//        setCenter(features);
        setLeft(new ProximityPane());
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

        addEventHandler(MouseEvent.MOUSE_CLICKED, event -> controller.endRelation(this));
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

    @Override
    public double getX() {
        return getAnchorXPos(getBoundsInParent().getMinX(), getBoundsInParent().getMaxX());
    }


    @Override
    public double getY() {
        return getAnchorYPos(getBoundsInParent().getMinY(), getBoundsInParent().getMaxY());
    }

    @Override
    public Tile getTile() {
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
        double mouseAnchorX;
        double mouseAnchorY;
        double initialTranslateX;
        double initialTranslateY;
    }

    private double getAnchorXPos(double min, double max) {
        return (max - min) / 2 + min;
    }

    private double getAnchorYPos(double min, double max) {
        return (max - min) * 2 / 3 + min;
    }

    private class ProximityPane extends Pane {
        public ProximityPane() {
            setId("proximitypane");
            setMinSize(20,20);
            setOnMouseEntered(event -> name.pseudoClassStateChanged(PseudoClass.getPseudoClass("proximity"), true));
            setOnMouseExited(event -> name.pseudoClassStateChanged(PseudoClass.getPseudoClass("proximity"), false));
            AnchorPoint anchorPoint = new AnchorPoint();
            getChildren().add(anchorPoint);
        }
    }

    private class AnchorPoint extends StackPane {

        public AnchorPoint() {
            Circle circle = new Circle(5);
            circle.setId("anchorpoint");

            Circle circle1 = new Circle(8, Color.AQUA);
            getChildren().addAll(circle1, circle);
        }
    }
}
