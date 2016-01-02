package ch.ceruleansands.seshat.language.java;

import ch.ceruleansands.actionstream.Action;
import ch.ceruleansands.actionstream.ActionHistory;
import ch.ceruleansands.seshat.Diagram;
import ch.ceruleansands.seshat.gui.Background;
import ch.ceruleansands.seshat.gui.GuiFactory;
import com.google.inject.Inject;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Represents a java diagram.
 * @author Thomsch.
 */
public class JavaDiagram implements Diagram {

    private GuiFactory guiFactory;
    private final Pane view;
    private List<MenuItem> editActions;
    private List<JavaTile> tiles;
    private ExporterImpl exporter;
    private final Group elements;

    private final ActionHistory history;

    @Inject
    public JavaDiagram(GuiFactory guiFactory, ExporterImpl exporter, ActionHistory history) {
        this.guiFactory = guiFactory;
        this.exporter = exporter;
        this.history = history;

        elements = new Group();
        tiles = new ArrayList<>();
        Background background = new Background(elements);
        view = new Pane(background, elements);

        background.widthProperty().bind(view.widthProperty());
        background.heightProperty().bind(view.heightProperty());

        editActions = new ArrayList<>();

        MenuItem itemNewClass = new MenuItem("_New class");
        itemNewClass.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        itemNewClass.setOnAction(event -> newClass());
        editActions.add(itemNewClass);
    }

    private void newClass() {
        JavaTile tile = new JavaTile(guiFactory);

        NewClass action = new NewClass(tiles, elements, tile);
        history.forward(action);
    }

    @Override
    public Node getView() {
        return view;
    }

    @Override
    public Optional<File> save(Optional<File> file) {
        return exporter.export(file, view.getScene().getWindow(), tiles);
    }

    @Override
    public List<MenuItem> getEditItems() {
        return editActions;
    }

    private class NewClass implements Action {

        private Collection<JavaTile> tiles;
        private final Group elements;
        private final JavaTile tile;

        /**
         * Creates a new instance of a newClassAction.
         * @param tiles The list of tiles currently on the model.
         * @param elements The GUI element containing the tiles.
         * @param tile The tile to add.
         */
        public NewClass(Collection<JavaTile> tiles, Group elements, JavaTile tile) {

            this.tiles = tiles;
            this.elements = elements;
            this.tile = tile;
        }

        @Override
        public void execute() {
            tiles.add(tile);
            elements.getChildren().add(tile.getView());
        }

        @Override
        public void revert() {
            tiles.remove(tile);
            elements.getChildren().remove(tile.getView());
        }
    }
}
