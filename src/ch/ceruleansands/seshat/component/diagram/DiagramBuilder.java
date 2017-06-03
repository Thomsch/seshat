package ch.ceruleansands.seshat.component.diagram;

import ch.ceruleansands.seshat.ui.SelectionBox;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Builds java diagrams.
 * @author Thomsch.
 */
public class DiagramBuilder {

    private final Provider<JavaDiagram> diagramProvider;


    @Inject
    public DiagramBuilder(Provider<JavaDiagram> diagramProvider) {
        this.diagramProvider = diagramProvider;
    }

    public JavaDiagram createEmpty() {
        return configureDiagram(diagramProvider.get());
    }

    private JavaDiagram configureDiagram(JavaDiagram diagram) {
        diagram.installSelector(new SelectionBox());
        diagram.installContextMenu();
        diagram.makeDraggable();
        return diagram;
    }
}
