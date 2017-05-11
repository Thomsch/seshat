package ch.ceruleansands.seshat.diagram;

import ch.ceruleansands.seshat.component.Relation;
import ch.ceruleansands.seshat.component.Tile;
import ch.ceruleansands.seshat.gui.EditableLabel;
import javafx.css.PseudoClass;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * @author Thomsch
 */
public class TestTile extends BorderPane implements Tile {

    private final EditableLabel name;
    private final DragContext dragContext;

    private final PseudoClass selectable = PseudoClass.getPseudoClass("selected");
    private final VBox content;

    public TestTile() {
        dragContext = new DragContext();

        setTranslateX(50);
        setTranslateY(50);

        name = new EditableLabel();
        name.setOnMouseEntered(event -> name.setCursor(Cursor.OPEN_HAND));
        Separator separator = new Separator();
        separator.setId("tileseparator");
        content = new VBox(name, separator);
        content.setId("tile");
        setCenter(content);

        setLeft(new ProximityPane());
        setTop(new ProximityPane());
        setRight(new ProximityPane());
        setBottom(new ProximityPane());

        setPrefSize(200, 200);

        name.addEventFilter(MouseEvent.MOUSE_PRESSED,
                mouseEvent -> {
                    // remember initial mouse cursor coordinates
                    // and node position
                    dragContext.mouseAnchorX = mouseEvent.getScreenX();
                    dragContext.mouseAnchorY = mouseEvent.getScreenY();
                    dragContext.initialTranslateX = getTranslateX();
                    dragContext.initialTranslateY = getTranslateY();
                    toFront();
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
    }

    @Override
    public Node getNode() {
        return this;
    }

    @Override
    public Relation getRelation() {
        return null;
    }

    private static final class DragContext {
        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }

    private class ProximityPane extends Pane {
        public ProximityPane() {
            setId("proximitypane");
            setMinSize(20,20);
            setOnMouseEntered(event -> content.pseudoClassStateChanged(PseudoClass.getPseudoClass("proximity"), true));
            setOnMouseExited(event -> content.pseudoClassStateChanged(PseudoClass.getPseudoClass("proximity"), false));
            AnchorPoint anchorPoint = new AnchorPoint();
            getChildren().add(anchorPoint);
        }
    }

    private class AnchorPoint extends StackPane{

        public AnchorPoint() {
            Circle circle = new Circle(5);
            circle.setId("anchorpoint");

            Circle circle1 = new Circle(8, Color.AQUA);
            getChildren().addAll(circle1, circle);
        }
    }
}
