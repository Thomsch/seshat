package ch.ceruleansands.seshat.language.java;

import ch.ceruleansands.seshat.component.Anchor;
import ch.ceruleansands.seshat.component.Diagram;
import ch.ceruleansands.seshat.component.Tile;
import ch.ceruleansands.seshat.component.TranslationTracker;
import ch.ceruleansands.seshat.gui.Background;
import ch.ceruleansands.seshat.gui.ErgonomicMenuItem;
import ch.ceruleansands.seshat.gui.Origin;
import ch.ceruleansands.seshat.gui.SelectionBox;
import ch.ceruleansands.seshat.language.java.action.ActionFactory;
import ch.ceruleansands.seshat.language.java.action.NewClass;
import ch.ceruleansands.seshat.language.java.io.ExporterImpl;
import com.google.inject.Inject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a java diagram.
 *
 * @author Thomsch.
 */
public class JavaDiagram implements Diagram {

    private final Pane view;
    private List<ErgonomicMenuItem> editActions;
    private Set<JavaTile> tiles;
    private Set<JavaRelationModel> relations;

    private ExporterImpl exporter;

    private final Group movingElements;
    private final Group tilesView;
    private final Group relationsView;
    private final TileFactory tileFactory;

    private final Collection<ErgonomicMenuItem> actions;
    private final Background background;
    private TranslationTracker translationTracker;
    private final IntegerProperty mouseX;
    private final IntegerProperty mouseY;

    private RelationBuilder relationBuilder;

    @Inject
    public JavaDiagram(ExporterImpl exporter, ActionFactory actionFactory, TileFactory tileFactory, RelationBuilder relationBuilder) {
        this.exporter = exporter;
        this.tileFactory = tileFactory;
        this.relationBuilder = relationBuilder;
        translationTracker = new TranslationTracker();

        Origin origin = new Origin();
        tilesView = new Group();
        relationsView = new Group();
        background = new Background();

        movingElements = new Group(tilesView, relationsView, origin);
        view = new Pane(background, movingElements);

        background.widthProperty().bind(view.widthProperty());
        background.heightProperty().bind(view.heightProperty());

        NewClass newClass = actionFactory.makeNewClass(this);

        editActions = new ArrayList<>();
        editActions.add(newClass);
        editActions.add(actionFactory.makeTestTile(this));

        actions = new ArrayList<>();
        actions.add(newClass);

        mouseX = new SimpleIntegerProperty();
        mouseY = new SimpleIntegerProperty();

        tiles = new HashSet<>();
        relations = new HashSet<>();

        // TODO Refactor #makeDraggable, #installContextMenu, #installSelector to assign them to each one of them to a mouse action because now we can use m1 and m2 at the same time :(
        relationGenerator();
        makeFocusable();
    }

    private void makeFocusable() {
        view.setFocusTraversable(true);
        view.setOnMouseEntered(event -> view.requestFocus());
//        view.setOnMouseClicked(event -> view.requestFocus());
    }

    private void relationGenerator() {

//        view.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//            if(relationBuilder.isRelationInProgress()) {
//                relationBuilder.cancel(relationsView);
//                highlightReceiverAnchors(false);
//            }
//        });

        view.addEventFilter(MouseEvent.MOUSE_MOVED, event -> {
            mouseX.setValue(event.getX() + translationTracker.getXTranslate());
            mouseY.setValue(event.getY() + translationTracker.getYTranslate());
        });
    }

    public void makeDraggable() {
        view.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                translationTracker.init(event.getX(), event.getY());
            }
        });

        view.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                translationTracker.update(event.getX(), event.getY());

                double tx = translationTracker.getAbsoluteXTranslation();
                double ty = translationTracker.getAbsoluteYTranslation();

                background.draw(tx, ty);
                movingElements.setTranslateX(tx);
                movingElements.setTranslateY(ty);
            }
        });

        view.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                translationTracker.end();
            }
        });

        background.widthProperty().addListener(evt -> {
            background.draw(translationTracker.getXTranslate(), translationTracker.getYTranslate());
        });
        background.heightProperty().addListener(evt -> {
            background.draw(translationTracker.getXTranslate(), translationTracker.getYTranslate());
        });
    }

    public void installContextMenu() {
        List<MenuItem> menuItems = actions.stream()
                .map(ErgonomicMenuItem::getAsMenuItem)
                .collect(Collectors.toList());
        ContextMenu contextMenu = new ContextMenu(menuItems.toArray(new MenuItem[menuItems.size()]));
        contextMenu.setAutoHide(true);

        view.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, event -> {
            contextMenu.show(view, event.getScreenX(), event.getScreenY());
            event.consume();
        });
        view.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            contextMenu.hide();
        });
    }

    public void installSelector(SelectionBox selectionBox) {
        view.getChildren().addAll(selectionBox);

        view.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.isPrimaryButtonDown()) {
                selectionBox.setStart(event.getX(), event.getY());
            }
        });
        view.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            if (event.isPrimaryButtonDown()) {
                selectionBox.setEnd(event.getX(), event.getY());
            }
        });
        view.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                tilesView.getChildren().forEach(node -> node.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false));
                List<Node> selected = selectionBox.release(tilesView.getChildren(), movingElements);
                selected.stream().forEach(node -> node.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true));
            }
        });
        selectionBox.toFront();
    }

    @Override
    public Node getView() {
        return view;
    }

    @Override
    public Optional<File> save(Optional<File> file) {
        return exporter.export(file, view.getScene().getWindow(), null);
    }

    @Override
    public List<ErgonomicMenuItem> getEditItems() {
        return editActions;
    }

    /**
     * Adds a new tile to the diagram from a model.
     *
     * @param model the model from which data will be displayed
     */
    public void addTile(JavaTileModel model) {
        JavaTile tile = tileFactory.createTile(this);
        tile.setName(model.getName());
        tile.getNode().setTranslateX(model.getX());
        tile.getNode().setTranslateY(model.getY());
        tiles.add(tile);
        addElement(tilesView, tile.getNode());
    }

    public void removeTile(Tile tile) {
        removeElement(tilesView, tile.getNode());
    }

    /**
     * Adds a new relation to the diagram from a model
     *
     * @param relation the model from which data will be displayed
     */
    public void addRelation(JavaRelationModel relation) {
        relations.add(relation);
    }

    public void startRelation(Anchor anchor) {
        if (!relationBuilder.isRelationInProgress()) {
            relationBuilder.start(anchor, mouseX, mouseY, relationsView);
            highlightReceiverAnchors(true);
        }
    }

    public void endRelation(Anchor anchor) {
        if (relationBuilder.isRelationInProgress()) {
            highlightReceiverAnchors(false);
            JavaRelationModel relation = relationBuilder.stop(anchor);
            addRelation(relation);
        }
    }

    private void highlightReceiverAnchors(boolean highlighted) {
        tiles.forEach(tile -> tile.highlightAnchors(highlighted));
    }

    private void addElement(Group group, Node node) {
        group.getChildren().add(node);
    }

    private void removeElement(Group group, Node node) {
        group.getChildren().removeAll(node);
    }

    public Point2D getMousePos() {
        return new Point2D(mouseX.doubleValue(), mouseY.doubleValue());
    }
}
