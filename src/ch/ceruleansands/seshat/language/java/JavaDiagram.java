package ch.ceruleansands.seshat.language.java;

import ch.ceruleansands.seshat.Diagram;
import ch.ceruleansands.seshat.gui.Background;
import ch.ceruleansands.seshat.gui.GuiFactory;
import ch.ceruleansands.seshat.gui.tile.Tile;
import ch.ceruleansands.seshat.model.Model;
import ch.ceruleansands.seshat.model.ModelObserver;
import ch.ceruleansands.seshat.model.Models;
import com.google.inject.Inject;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;

/**
 * Represents a java diagram.
 * @author Thomsch.
 */
public class JavaDiagram implements Diagram, ModelObserver {

    private Model model;
    private Group elements;
    private GuiFactory guiFactory;
    private final Pane diagramView;
    private final MenuController menuController;

    @Inject
    public JavaDiagram(GuiFactory guiFactory, MenuController menuController) {
        this.guiFactory = guiFactory;
        this.menuController = menuController;
        this.model = Models.createEmpty();

        model.addObserver(this);

        elements = new Group();
        Background background = new Background(elements);
        diagramView = new Pane(background, elements);

        background.widthProperty().bind(diagramView.widthProperty());
        background.heightProperty().bind(diagramView.heightProperty());
    }

    @Override
    public Node getView() {
        return diagramView;
    }

    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public void config(Menu menuEdit, MenuItem itemSave, MenuItem itemLoad) {
        MenuItem itemNewClass = new MenuItem("_New class");
        itemNewClass.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        itemNewClass.setOnAction(menuController.createNewClass(model));
        menuEdit.getItems().add(itemNewClass);

        itemSave.setOnAction(menuController.save(this));
    }


    @Override
    public void onNewClass(Clazz clazz) {
        Tile tile = guiFactory.makeTile(clazz);

        elements.getChildren().addAll(tile);
    }
}
