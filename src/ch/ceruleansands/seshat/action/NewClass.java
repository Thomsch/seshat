package ch.ceruleansands.seshat.action;

import ch.ceruleansands.seshat.component.diagram.Diagram;
import ch.ceruleansands.seshat.component.menu.ErgonomicMenuItem;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;


/**
 * @author Thomsch
 */
public class NewClass extends ErgonomicMenuItem {
    private final Diagram diagram;

    /**
     * Creates a new instance of a newClassAction.
     */
    @Inject
    public NewClass(@Assisted Diagram diagram) {
        this.diagram = diagram;
    }

    @Override
    public String getTitle() {
        return "_New class";
    }

    @Override
    public KeyCombination getAccelerator() {
        return new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
    }

    @Override
    public EventHandler<ActionEvent> getAction() {
        return event -> {
            diagram.addTile();
        };
    }
}
