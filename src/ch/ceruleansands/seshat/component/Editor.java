package ch.ceruleansands.seshat.component;

import ch.ceruleansands.seshat.action.ActionFactory;
import ch.ceruleansands.seshat.gui.MenuProxy;
import ch.ceruleansands.seshat.gui.TabManager;
import ch.ceruleansands.seshat.language.LanguageHandler;
import ch.ceruleansands.seshat.language.LanguageInitializer;
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

    private final MenuProxy menu;
    private final TabManager tabManager;

    private final ActionFactory actionFactory;
    private final DiagramBuilder diagramBuilder;

    private final LanguageHandler languageHandler;
    private final LanguageInitializer languageInitializer;

    @Inject
    // TODO Evaluate the need for a builder.
    public Editor(@Assisted Stage stage, LanguageInitializer languageInitializer, DiagramBuilder javaDiagramBuilder, MenuProxy menu, TabManager tabManager, ActionFactory actionFactory, LanguageHandler languageHandler) {
        this.stage = stage;
        this.languageInitializer = languageInitializer;
        this.diagramBuilder = javaDiagramBuilder;
        this.menu = menu;
        this.actionFactory = actionFactory;
        this.languageHandler = languageHandler;
        this.tabManager = tabManager;
        root = new BorderPane();
    }

    public void configure() {
        languageInitializer.loadLanguages();

        // File
        menu.addFileItem(actionFactory.makeLoadAction());
        menu.addFileItem(actionFactory.makeSaveAction());
        menu.addFileSeparator();
        this.languageHandler.addNewDiagramOnMenu(menu);

        // Edit
        menu.addEditItem(actionFactory.makeUndoAction());
        menu.addEditItem(actionFactory.makeRedoAction());
        menu.addEditSeparator();
        stage.setTitle("Seshat - v.0.1");
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
