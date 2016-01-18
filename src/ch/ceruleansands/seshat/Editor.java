package ch.ceruleansands.seshat;

import ch.ceruleansands.seshat.action.ActionFactory;
import ch.ceruleansands.seshat.gui.MenuProxy;
import ch.ceruleansands.seshat.gui.TabManager;
import ch.ceruleansands.seshat.language.java.DiagramBuilder;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author Thomsch.
 */
public class Editor {

    private final Stage stage;
    private final BorderPane root;

    private final DiagramBuilder diagramBuilder;
    private final MenuProxy menu;

    private final TabManager tabManager;
    private final LanguageHandler languageHandler;

    @Inject
    // TODO Evaluate the need for a builder.
    public Editor(@Assisted Stage stage, DiagramBuilder javaDiagramBuilder, MenuProxy menu, TabManager tabManager, ActionFactory actionFactory, LanguageHandler languageHandler) {
        this.stage = stage;
        this.diagramBuilder = javaDiagramBuilder;
        this.menu = menu;
        this.languageHandler = languageHandler;
        root = new BorderPane();

        this.tabManager = tabManager;

        // File
        menu.addFileItem(actionFactory.makeLoadAction());
        menu.addFileItem(actionFactory.makeSaveAction());
        menu.addFileSeparator();
        this.languageHandler.addNewDiagramOnMenu(menu);

        // Edit
        menu.addEditItem(actionFactory.makeUndoAction());
        menu.addEditItem(actionFactory.makeRedoAction());
        menu.addEditSeparator();
    }

    public void configure() {
        stage.setTitle("Seshat - V.0.0");
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("style.css");

        root.setTop(menu.getMenuBar());
        root.setCenter(tabManager.getTabPane());

        stage.setScene(scene);
//        stage.setMaximized(true);
        tabManager.addDiagram(diagramBuilder.createEmpty());
    }

    /**
     * Shows the GUI of the editor.
     */
    public void show() {
        stage.show();
    }
}
