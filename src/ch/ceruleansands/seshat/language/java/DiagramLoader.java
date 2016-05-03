package ch.ceruleansands.seshat.language.java;

import ch.ceruleansands.seshat.Diagram;
import ch.ceruleansands.seshat.loader.LanguageDiagramLoader;
import com.google.inject.Inject;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Load diagrams for the Java language.
 * @author Thomsch
 */
class DiagramLoader implements LanguageDiagramLoader {

    private final ParserLoader parserLoader;
    private final DiagramBuilder diagramBuilder;

    @Inject
    public DiagramLoader(ParserLoader parserLoader, DiagramBuilder diagramBuilder) {
        this.parserLoader = parserLoader;
        this.diagramBuilder = diagramBuilder;
    }

    @Override
    public Diagram loadFromBuffer(BufferedReader bufferedReader) throws IOException, SaveFormatException {
        JavaDiagram diagram = diagramBuilder.createEmpty();
        parserLoader.load(diagram, bufferedReader);
        return diagram;
    }
}
