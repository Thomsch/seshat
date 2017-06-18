package ch.ceruleansands.seshat.component.diagram;

import ch.ceruleansands.seshat.action.ActionFactory;
import ch.ceruleansands.seshat.action.NewClass;
import ch.ceruleansands.seshat.component.menu.ErgonomicMenuItem;
import ch.ceruleansands.seshat.component.tile.ProximityPane;
import ch.ceruleansands.seshat.component.tile.Tile;
import ch.ceruleansands.seshat.component.tile.TileModel;
import ch.ceruleansands.seshat.disabled.io.exporter.ExporterImpl;
import ch.ceruleansands.seshat.ui.Background;
import ch.ceruleansands.seshat.ui.Origin;
import ch.ceruleansands.seshat.ui.SelectionBox;
import com.google.inject.Inject;
import com.google.inject.Provider;
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
public class Diagram {

    private final Pane view;
    private List<ErgonomicMenuItem> editActions;
    private Set<Tile> tiles;
    private ExporterImpl exporter;

    private final Group movingElements;

    private final Group tilesView;
    private final Group relationsView;
    private final Group proximityView;
    private final Provider<Tile> tileProvider;

    private final Collection<ErgonomicMenuItem> actions;
    private final Background background;
    private TranslationTracker translationTracker;
    private final IntegerProperty mouseX;
    private final IntegerProperty mouseY;

    @Inject
    public Diagram(ExporterImpl exporter, ActionFactory actionFactory, Provider<Tile> tileProvider) {
        this.exporter = exporter;
        this.tileProvider = tileProvider;
        translationTracker = new TranslationTracker();

        Origin origin = new Origin();
        tilesView = new Group();
        relationsView = new Group();
        proximityView = new Group();
        background = new Background();

        movingElements = new Group(proximityView, tilesView, relationsView, origin);
        view = new Pane(background, movingElements);

        background.widthProperty().bind(view.widthProperty());
        background.heightProperty().bind(view.heightProperty());

        NewClass newClass = actionFactory.makeNewClass(this);

        editActions = new ArrayList<>();
        editActions.add(newClass);

        actions = new ArrayList<>();
        actions.add(newClass);

        mouseX = new SimpleIntegerProperty();
        mouseY = new SimpleIntegerProperty();

        tiles = new HashSet<>();

        makeFocusable();
        trackMousePostion();
    }

    private void makeFocusable() {
        view.setFocusTraversable(true);
        view.setOnMouseEntered(event -> view.requestFocus());
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

        background.widthProperty().addListener(evt -> background.draw(translationTracker.getXTranslate(), translationTracker.getYTranslate()));
        background.heightProperty().addListener(evt -> background.draw(translationTracker.getXTranslate(), translationTracker.getYTranslate()));
    }

    private void trackMousePostion() {

        view.addEventFilter(MouseEvent.MOUSE_MOVED, event -> {
            mouseX.setValue(event.getX() + translationTracker.getXTranslate());
            mouseY.setValue(event.getY() + translationTracker.getYTranslate());
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
        view.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> contextMenu.hide());
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

    public Node getView() {
        return view;
    }

    public Optional<File> save(Optional<File> file) {
        return exporter.export(file, view.getScene().getWindow(), null);
    }

    public List<ErgonomicMenuItem> getEditItems() {
        return editActions;
    }

    /**
     * Adds a new tile to the diagram from a model.
     *
     * @param model the model from which data will be displayed
     */
    public void addTile(TileModel model) {
        final Tile tile = tileProvider.get();
        tile.setName(model.getName());
        tile.getNode().setTranslateX(model.getX());
        tile.getNode().setTranslateY(model.getY());
        tiles.add(tile);
        addElement(tilesView, tile.getNode());

        final ProximityPane prox = new ProximityPane(tile);
        proximityView.getChildren().add(prox);
    }

    public void removeTile(Tile tile) {
        removeElement(tilesView, tile.getNode());
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
