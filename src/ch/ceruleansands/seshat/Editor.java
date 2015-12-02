package ch.ceruleansands.seshat;

import ch.ceruleansands.seshat.gui.GuiFactory;
import ch.ceruleansands.seshat.language.java.JavaDiagram;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author Thomsch.
 */
public class Editor {

    private final Stage stage;
    private final GuiFactory guiFactory;
    private final BorderPane root;

    Diagram current = null;
    private Menu menuEdit;
    private MenuItem itemSave;
    private MenuItem itemLoad;

    @Inject
    public Editor(@Assisted Stage stage, GuiFactory guiFactory) {
        this.stage = stage;
        this.guiFactory = guiFactory;
        root = new BorderPane();
    }

    public void configure() {
        stage.setTitle("Seshat - V.0.0");
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("style.css");

        MenuBar menus = makeMenu(this);
//        ToolBar toolBar = makeToolbar();


//        root.setRight(toolBar);
        root.setTop(menus);

        stage.setScene(scene);
//        stage.setMaximized(true);
    }

    public void addDiagram(Diagram diagram) {
        current = diagram;

        root.setCenter(diagram.getView());

        // Customize editor for the current diagram.
        diagram.config(menuEdit, itemSave, itemLoad);
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
            DiagramFactory diagramFactory = guiFactory.makeDiagramFactory();
            JavaDiagram javaDiagram = diagramFactory.createJavaDiagram();
            editor.addDiagram(javaDiagram);
        });

        menuFile.getItems().addAll(itemSave, itemLoad, itemJavaDiagram);

        menuEdit = new Menu("_Edit");
        Menu menuView = new Menu("Help");
        Menu menuDebug = new Menu("Debug");

        menuBar.getMenus().addAll(menuFile, menuEdit, menuView, menuDebug);
        return menuBar;
    }
}
