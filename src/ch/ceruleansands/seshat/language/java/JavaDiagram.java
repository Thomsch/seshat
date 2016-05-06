package ch.ceruleansands.seshat.language.java;

import ch.ceruleansands.seshat.Diagram;
import ch.ceruleansands.seshat.SelectionBox;
import ch.ceruleansands.seshat.gui.Background;
import ch.ceruleansands.seshat.gui.ErgonomicMenuItem;
import ch.ceruleansands.seshat.gui.Origin;
import ch.ceruleansands.seshat.language.java.action.ActionFactory;
import ch.ceruleansands.seshat.language.java.action.NewClass;
import ch.ceruleansands.seshat.tilediagram.Tile;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents a java diagram.
 * @author Thomsch.
 */
public class JavaDiagram implements Diagram {

    private final Pane view;
    private List<ErgonomicMenuItem> editActions;
    private ExporterImpl exporter;

    private final Group movingElements;
    private final Group tiles;
    private final Group relations;
    private final Provider<JavaTile> javaTileProvider;

    private final Collection<ErgonomicMenuItem> actions;
    private final Background background;
    private TranslationTracker translationTracker;

    @Inject
    public JavaDiagram(ExporterImpl exporter, ActionFactory actionFactory, Provider<JavaTile> javaTileProvider) {
        this.exporter = exporter;
        this.javaTileProvider = javaTileProvider;
        translationTracker = new TranslationTracker();

        Origin origin = new Origin();
        tiles = new Group();
        relations = null;
        background = new Background();

        movingElements = new Group(tiles, origin);
        view = new Pane(background, movingElements);

        background.widthProperty().bind(view.widthProperty());
        background.heightProperty().bind(view.heightProperty());

        NewClass newClass = actionFactory.makeNewClass(this);

        editActions = new ArrayList<>();
        editActions.add(newClass);
        editActions.add(actionFactory.makeTestTile(this));

        actions = new ArrayList<>();
        actions.add(newClass);

//        view.setOnScroll(event -> {
//            if(event.isControlDown()) {
//                double v = event.getDeltaY() / 1000;
//                view.setScaleX(view.getScaleX() + v);
//                view.setScaleY(view.getScaleY() + v);
//                view.setScaleZ(view.getScaleZ() + v);
//                System.out.println("event.getDeltaY() = " + event.getDeltaY());
//            }
//        });

        // TODO Refactor #makeDraggable, #installContextMenu, #installSelector to assign them to each one of them to a mouse action because now we can use m1 and m2 at the same time :(
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
            if(event.isPrimaryButtonDown()) {
                selectionBox.setStart(event.getX(), event.getY());
            }
        });
        view.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            if(event.isPrimaryButtonDown()) {
                selectionBox.setEnd(event.getX(), event.getY());
            }
        });
        view.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                tiles.getChildren().forEach(node -> node.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false));
                List<Node> selected = selectionBox.release(tiles.getChildren(), movingElements);
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

    public void addTile(Tile tile) {
        addElement(tiles, tile.getNode());
    }

    public void removeTile(Tile tile) {
        removeElement(tiles, tile.getNode());
    }

    public void addElement(Group group, Node node) {
        group.getChildren().add(node);
    }

    public void removeElement(Group group, Node node) {
        group.getChildren().removeAll(node);
    }

    /**
     * Adds a new tile to the diagram from a model.
     * @param model the model from which data will be displayed
     */
    public void addTile(JavaTileModel model) {
        JavaTile tile = javaTileProvider.get();
        tile.setName(model.getName());
        tile.getNode().setTranslateX(model.getGraphic().x);
        tile.getNode().setTranslateY(model.getGraphic().y);
        addTile(tile);
    }

    /**
     * Adds a new relation to the diagram from a model
     * @param relation the model from which data will be displayed
     */
    public void addRelation(JavaRelationModel relation) {

    }
}
