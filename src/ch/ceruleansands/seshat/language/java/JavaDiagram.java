package ch.ceruleansands.seshat.language.java;

import ch.ceruleansands.seshat.Diagram;
import ch.ceruleansands.seshat.gui.Background;
import ch.ceruleansands.seshat.gui.ErgonomicMenuItem;
import ch.ceruleansands.seshat.language.java.action.ActionFactory;
import com.google.inject.Inject;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a java diagram.
 * @author Thomsch.
 */
public class JavaDiagram implements Diagram {

    private final Pane view;
    private List<ErgonomicMenuItem> editActions;
    private List<JavaTile> tiles;
    private ExporterImpl exporter;
    private final Group elements;

    @Inject
    public JavaDiagram(ExporterImpl exporter, ActionFactory actionFactory) {
        this.exporter = exporter;

        elements = new Group();
        tiles = new ArrayList<>();
        Background background = new Background(elements);
        view = new Pane(background, elements);

        background.widthProperty().bind(view.widthProperty());
        background.heightProperty().bind(view.heightProperty());

        editActions = new ArrayList<>();
        editActions.add(actionFactory.makeNewClass(tiles, elements));
    }

    @Override
    public Node getView() {
        return view;
    }

    @Override
    public Optional<File> save(Optional<File> file) {
        return exporter.export(file, view.getScene().getWindow(), tiles);
    }

    @Override
    public List<ErgonomicMenuItem> getEditItems() {
        return editActions;
    }
}
