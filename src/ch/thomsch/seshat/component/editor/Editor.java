package ch.thomsch.seshat.component.editor;

import ch.thomsch.seshat.action.ActionFactory;
import ch.thomsch.seshat.component.diagram.Diagram;
import ch.thomsch.seshat.component.diagram.DiagramBuilder;
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
}
