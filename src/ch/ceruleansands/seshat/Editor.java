package ch.ceruleansands.seshat;

import ch.ceruleansands.seshat.gui.DiagramTab;
import ch.ceruleansands.seshat.gui.GuiFactory;
import ch.ceruleansands.seshat.language.java.JavaDiagram;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author Thomsch.
 */
public class Editor implements ChangeListener<Tab>{

    private final Stage stage;
    private final GuiFactory guiFactory;
    private final BorderPane root;
    private final TabPane tabPane;

    private Menu menuEdit;
    private MenuItem itemSave;
    private MenuItem itemLoad;

    @Inject
    public Editor(@Assisted Stage stage, GuiFactory guiFactory) {
        this.stage = stage;
        this.guiFactory = guiFactory;
        root = new BorderPane();
        tabPane = new TabPane();

        ReadOnlyObjectProperty<Tab> selectedItemProperty = tabPane.getSelectionModel().selectedItemProperty();
        selectedItemProperty.addListener(this);
    }

    public void configure() {
        stage.setTitle("Seshat - V.0.0");
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("style.css");

        MenuBar menus = makeMenu(this);
//        ToolBar toolBar = makeToolbar();

//        root.setRight(toolBar);
        addJavaDiagram();
        root.setTop(menus);
        root.setCenter(tabPane);

        stage.setScene(scene);
//        stage.setMaximized(true);
    }

    /**
     * Shows the GUI of the editor.
     */
    public void show() {
        stage.show();
    }


    private ToolBar makeToolbar() {
        return new ToolBar(new Button("hello"), new Label("sss"));
    }

    private MenuBar makeMenu(Editor editor) {
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("_File");

        itemSave = new MenuItem("_Save...");

        itemLoad = new MenuItem("_Load...");

        MenuItem itemJavaDiagram = new MenuItem("_New java diagram");
        itemJavaDiagram.setOnAction(event -> {
            addJavaDiagram();
        });

        menuFile.getItems().addAll(itemSave, itemLoad, itemJavaDiagram);

        menuEdit = new Menu("_Edit");
        Menu menuView = new Menu("Help");

        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        return menuBar;
    }

    private void addJavaDiagram() {
        DiagramFactory diagramFactory = guiFactory.makeDiagramFactory();
        JavaDiagram javaDiagram = diagramFactory.createJavaDiagram();

        DiagramTab tab = new DiagramTab("Untilited diagram", javaDiagram);
        tabPane.getTabs().add(tab);
    }

    @Override
    public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
        if(oldValue != null) {
            DiagramTab oldDiagram = (DiagramTab) oldValue;
            menuEdit.getItems().removeAll(oldDiagram.getEditItems());
            itemSave.setDisable(true);
            itemLoad.setDisable(true);
        }

        if(newValue != null) {
            DiagramTab newDiagram = (DiagramTab) newValue;
            menuEdit.getItems().addAll(newDiagram.getEditItems());
            itemSave.setOnAction(newDiagram.getOnSaveAction());
            itemSave.setDisable(false);
            itemLoad.setDisable(false);
        }
    }
}
