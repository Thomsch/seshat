package ch.ceruleansands.seshat;

import ch.ceruleansands.seshat.language.java.JavaDiagram;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Creates diagrams.
 * @author Thomsch.
 */
public class DiagramFactory {

    private final Provider<JavaDiagram> javaDiagramProvider;

    @Inject
    public DiagramFactory(Provider<JavaDiagram> javaDiagramProvider) {
        this.javaDiagramProvider = javaDiagramProvider;
    }

    /**
     * Creates an instance of a java diagram.
     * @return The
     */
    public JavaDiagram createJavaDiagram() {
        return javaDiagramProvider.get();
    }
}
