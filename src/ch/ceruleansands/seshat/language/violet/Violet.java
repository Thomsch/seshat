package ch.ceruleansands.seshat.language.violet;

import ch.ceruleansands.seshat.component.Diagram;
import ch.ceruleansands.seshat.gui.ErgonomicMenuItem;
import ch.ceruleansands.seshat.io.loader.LanguageDiagramLoader;
import ch.ceruleansands.seshat.language.Language;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Violet is a diagram language to test the editor. It can have tiles with relations between them. Each tile can have multiple items (methods, attributes, whatever).
 *
 * @author Thomsch
 */
public class Violet implements Language {
    @Override
    public String getName() {
        return "Violet";
    }

    @Override
    public ErgonomicMenuItem getNewDiagramAction() {
        return new ErgonomicMenuItem() {
            @Override
            public String getTitle() {
                return "_New violet diagram";
            }

            @Override
            public KeyCombination getAccelerator() {
                return null;
            }

            @Override
            public EventHandler<ActionEvent> getAction() {
                return event -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("I'm a test");
                    alert.showAndWait();
                };
            }
        };
    }

    @Override
    public LanguageDiagramLoader getLanguageDiagramLoader() {
        return new LanguageDiagramLoader() {
            @Override
            public Diagram loadFromBuffer(BufferedReader bufferedReader) {
                return new Diagram() {
                    @Override
                    public Node getView() {
                        return new Pane();
                    }

                    @Override
                    public List<ErgonomicMenuItem> getEditItems() {
                        return Collections.emptyList();
                    }

                    @Override
                    public Optional<File> save(Optional<File> file) {
                        return Optional.empty();
                    }
                };
            }
        };
    }
}
