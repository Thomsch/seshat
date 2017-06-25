package ch.ceruleansands.seshat.component.editor;

import ch.ceruleansands.seshat.action.ActionFactory;
import ch.ceruleansands.seshat.component.diagram.Diagram;
import ch.ceruleansands.seshat.component.diagram.DiagramBuilder;
import ch.ceruleansands.seshat.component.menu.MainMenu;
import com.google.inject.Inject;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.util.Objects;

/**
 * @author Thomsch.
 */
public class Editor {

    private final DiagramBuilder diagramBuilder;
    private final ActionFactory actionFactory;

    private BorderPane view;

    @Inject
    public Editor(DiagramBuilder javaDiagramBuilder, ActionFactory actionFactory) {
        this.diagramBuilder = javaDiagramBuilder;
        this.actionFactory = actionFactory;
    }

    /**
     * The editor creates it's view. Set it for himself and returns it
     *
     * @return The view created and used for the editor
     */
    public Parent createView() {
        final BorderPane borderPane = new BorderPane();
        this.view = borderPane;
        return borderPane;
    }

    public void setEmptyDiagram() {
        Objects.requireNonNull(view);

        final Diagram diagram = diagramBuilder.createEmpty();
        view.setCenter(diagram.getView());
    }

    public void setMenu() {
        Objects.requireNonNull(view);

        final MainMenu menu = new MainMenu();

        menu.addEditItem(actionFactory.makeUndoAction());
        menu.addEditItem(actionFactory.makeRedoAction());
    }
}
