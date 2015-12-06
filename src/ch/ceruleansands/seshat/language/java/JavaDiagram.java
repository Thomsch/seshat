package ch.ceruleansands.seshat.language.java;

import ch.ceruleansands.seshat.Diagram;
import ch.ceruleansands.seshat.gui.Background;
import ch.ceruleansands.seshat.gui.GuiFactory;
import ch.ceruleansands.seshat.gui.tile.Tile;
import ch.ceruleansands.seshat.model.Model;
import ch.ceruleansands.seshat.model.ModelObserver;
import ch.ceruleansands.seshat.model.Models;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a java diagram.
 * @author Thomsch.
 */
public class JavaDiagram implements Diagram, ModelObserver {

    private Model model;
    private Group elements;
    private GuiFactory guiFactory;
    private final Pane diagramView;
    private final MenuActions menuActions;
    private EventHandler<ActionEvent> saveAction;
    private List<MenuItem> editActions;

    @Inject
    public JavaDiagram(GuiFactory guiFactory, MenuActions menuActions) {
        this.guiFactory = guiFactory;
        this.menuActions = menuActions;
        this.model = Models.createEmpty();

        model.addObserver(this);

        elements = new Group();
        Background background = new Background(elements);
        diagramView = new Pane(background, elements);

        background.widthProperty().bind(diagramView.widthProperty());
        background.heightProperty().bind(diagramView.heightProperty());

        saveAction = menuActions.save(this);
        editActions = new ArrayList<>();

        MenuItem itemNewClass = new MenuItem("_New class");
        itemNewClass.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        itemNewClass.setOnAction(menuActions.createNewClass(model));
        editActions.add(itemNewClass);
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

    }

    @Override
    public EventHandler<ActionEvent> getOnSaveAction() {
        return saveAction;
    }

    @Override
    public List<MenuItem> getEditItems() {
        return editActions;
    }


    @Override
    public void onNewClass(Clazz clazz) {
        Tile tile = guiFactory.makeTile(clazz);

        elements.getChildren().addAll(tile);
    }
}
