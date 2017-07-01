package ch.thomsch.seshat.component.diagram;

import ch.thomsch.seshat.ui.SelectionBox;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Builds java diagrams.
 * @author Thomsch.
 */
public class DiagramBuilder {

    private final Provider<Diagram> diagramProvider;


    @Inject
    public DiagramBuilder(Provider<Diagram> diagramProvider) {
        this.diagramProvider = diagramProvider;
    }

    public Diagram createEmpty() {
        return configureDiagram(diagramProvider.get());
    }

    private Diagram configureDiagram(Diagram diagram) {
        diagram.installSelector(new SelectionBox());
        diagram.installContextMenu();
        diagram.makeDraggable();
        return diagram;
    }
}
