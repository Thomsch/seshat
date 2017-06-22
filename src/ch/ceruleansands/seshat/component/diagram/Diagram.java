package ch.ceruleansands.seshat.component.diagram;

import ch.ceruleansands.seshat.action.ActionFactory;
import ch.ceruleansands.seshat.action.NewClass;
import ch.ceruleansands.seshat.component.menu.ErgonomicMenuItem;
import ch.ceruleansands.seshat.component.tile.ProximityPane;
import ch.ceruleansands.seshat.component.tile.Tile;
import ch.ceruleansands.seshat.component.tile.TileModel;
import ch.ceruleansands.seshat.ui.Background;
import ch.ceruleansands.seshat.ui.Origin;
import ch.ceruleansands.seshat.ui.SelectionBox;
import com.google.inject.Inject;
import com.google.inject.Provider;
import javafx.css.PseudoClass;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.*;

/**
 * Represents a java diagram.
 *
 * @author Thomsch.
 */
public class Diagram {

    public final Pane view;
    private final Group movingElements;
    private final Group tilesView;
    private final Group proximityView;
    private final Provider<Tile> tileProvider;
    private final Collection<ErgonomicMenuItem> actions;
    private final Background background;
    private final TilePlacer tilePlacer;
    private List<ErgonomicMenuItem> editActions;
    private Set<Tile> tiles;
    private TranslationTracker translationTracker;

    @Inject
    public Diagram(ActionFactory actionFactory, Provider<Tile> tileProvider, TilePlacer tilePlacer) {
        this.tileProvider = tileProvider;
        this.tilePlacer = tilePlacer;
        translationTracker = new TranslationTracker();

        final Origin origin = new Origin();
        tilesView = new Group();
        proximityView = new Group();
        background = new Background();

        movingElements = new Group(proximityView, tilesView, origin);
        view = new Pane(background, movingElements);

        background.widthProperty().bind(view.widthProperty());
        background.heightProperty().bind(view.heightProperty());

        final NewClass newClass = actionFactory.makeNewClass(this);

        editActions = new ArrayList<>();
        editActions.add(newClass);

        actions = new ArrayList<>();
        actions.add(newClass);



        tiles = new HashSet<>();

        view.addEventFilter(MouseEvent.MOUSE_MOVED, tilePlacer);

        makeFocusable();
    }

    private void makeFocusable() {
        view.setFocusTraversable(true);
        view.setOnMouseEntered(event -> view.requestFocus());
    }

    void makeDraggable() {
        view.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                translationTracker.init(event.getX(), event.getY());
            }
        });

        view.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                translationTracker.update(event.getX(), event.getY());

                final double tx = translationTracker.getAbsoluteXTranslation();
                final double ty = translationTracker.getAbsoluteYTranslation();

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

    void installContextMenu() {
        final MenuItem[] menuItems = actions.stream()
                .map(ErgonomicMenuItem::getAsMenuItem)
                .toArray(MenuItem[]::new);

        final ContextMenu contextMenu = new ContextMenu(menuItems);
        contextMenu.setAutoHide(true);

        view.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, event -> {
            contextMenu.show(view, event.getScreenX(), event.getScreenY());
            event.consume();
        });
        view.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> contextMenu.hide());
    }

    void installSelector(SelectionBox selectionBox) {
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
                final List<Node> selected = selectionBox.release(tilesView.getChildren(), movingElements);
                selected.stream().forEach(node -> node.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true));
            }
        });
        selectionBox.toFront();
    }

    Node getView() {
        return view;
    }

    List<ErgonomicMenuItem> getEditItems() {
        return editActions;
    }

    /**
     * Adds a new tile to the diagram from a model.
     *
     */
    public void addTile() {
        final TileModel model = new TileModel("Unamed tile",
                tilePlacer.positionForX(translationTracker.getXTranslate()),
                tilePlacer.positionForY(translationTracker.getYTranslate()));
        addTile(model);
    }

    public void removeTile(Tile tile) {
        removeElement(tilesView, tile.getNode());
    }

    private void addElement(Group group, Node node) {
        group.getChildren().add(node);
    }

    private void removeElement(Group group, Node node) {
        group.getChildren().removeAll(node);
    }

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
}
