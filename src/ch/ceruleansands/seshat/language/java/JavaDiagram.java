package ch.ceruleansands.seshat.language.java;

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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a java diagram.
 * @author Thomsch.
 */
public class JavaDiagram implements Diagram {

    private GuiFactory guiFactory;
    private final Pane view;
    private List<MenuItem> editActions;
    private List<JavaTile> tiles;

    @Inject
    public JavaDiagram(GuiFactory guiFactory) {
        this.guiFactory = guiFactory;

        Group elements = new Group();
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
        tiles.add(tile);
        view.getChildren().add(tile.getView());
    }

    @Override
    public Node getView() {
        return view;
    }

    @Override
    public void save() {
        File file = new File("test.ses");
        try {
            PrintWriter writer = new PrintWriter(file);
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Failed to create new file.");
            }

            tiles.forEach(tile -> {
                writer.println(tile.getName());
                tile.getAttributes().forEach(attribute -> {
                    writer.println("    " + attribute);
                });

                tile.getMethods().forEach(method -> {
                    writer.println("    " + method);
                });
            } );
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MenuItem> getEditItems() {
        return editActions;
    }
}
