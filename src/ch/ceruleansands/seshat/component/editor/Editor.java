package ch.ceruleansands.seshat.component.editor;

import ch.ceruleansands.seshat.action.ActionFactory;
import ch.ceruleansands.seshat.component.diagram.Diagram;
import ch.ceruleansands.seshat.component.diagram.DiagramBuilder;
import ch.ceruleansands.seshat.component.menu.MainMenu;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author Thomsch.
 */
public class Editor {

    private final Stage stage;
    private final BorderPane root;

    private final MainMenu menu;

    private final ActionFactory actionFactory;
    private final DiagramBuilder diagramBuilder;

    @Inject
    // TODO Evaluate the need for a builder.
    public Editor(@Assisted Stage stage, DiagramBuilder javaDiagramBuilder, MainMenu menu, ActionFactory actionFactory) {
        this.stage = stage;
        this.diagramBuilder = javaDiagramBuilder;
        this.menu = menu;
        this.actionFactory = actionFactory;
        root = new BorderPane();
    }

    public void configure() {
        // Edit
        menu.addEditItem(actionFactory.makeUndoAction());
        menu.addEditItem(actionFactory.makeRedoAction());
        menu.addEditSeparator();
        stage.setTitle("Seshat - v.0.1");
        stage.getIcons().addAll(
                new Image("icon/16.png"),
                new Image("icon/24.png"),
                new Image("icon/32.png"),
                new Image("icon/48.png"),
                new Image("icon/128.png"));

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("style.css");

        root.setTop(menu.getMenuBar());

        stage.setScene(scene);
//        stage.setMaximized(true);
    }

    /**
     * Shows the GUI of the editor.
     */
    public void show() {
        stage.show();
    }

    public void emptyDiagram() {
        final Diagram diagram = diagramBuilder.createEmpty();
        root.setCenter(diagram.view);
    }
}
