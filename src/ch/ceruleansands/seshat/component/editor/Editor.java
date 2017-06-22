package ch.ceruleansands.seshat.component.editor;

import ch.ceruleansands.seshat.action.ActionFactory;
import ch.ceruleansands.seshat.component.diagram.Diagram;
import ch.ceruleansands.seshat.component.diagram.DiagramBuilder;
import ch.ceruleansands.seshat.component.menu.MainMenu;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import javafx.scene.layout.BorderPane;

/**
 * @author Thomsch.
 */
public class Editor {

    private final BorderPane view;
    private final DiagramBuilder diagramBuilder;
    private final ActionFactory actionFactory;

    @Inject
    // TODO Evaluate the need for a builder.
    public Editor(@Assisted BorderPane view, DiagramBuilder javaDiagramBuilder, ActionFactory actionFactory) {
        this.view = view;
        this.diagramBuilder = javaDiagramBuilder;
        this.actionFactory = actionFactory;
    }

    public void setEmptyDiagram() {
        final Diagram diagram = diagramBuilder.createEmpty();
        view.setCenter(diagram.view);
    }

    public void setMenu() {
        final MainMenu menu = new MainMenu();

        menu.addEditItem(actionFactory.makeUndoAction());
        menu.addEditItem(actionFactory.makeRedoAction());
    }
}
