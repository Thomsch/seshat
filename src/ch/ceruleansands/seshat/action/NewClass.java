package ch.ceruleansands.seshat.action;

import ch.ceruleansands.actionstream.ActionHistory;
import ch.ceruleansands.seshat.diagram.JavaDiagram;
import ch.ceruleansands.seshat.gui.ErgonomicMenuItem;
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
    private final JavaDiagram javaDiagram;
    private final ActionHistory history;
    private final ActionFactory actionFactory;

    /**
     * Creates a new instance of a newClassAction.
     */
    @Inject
    public NewClass(@Assisted JavaDiagram javaDiagram, ActionHistory history, ActionFactory actionFactory) {
        this.javaDiagram = javaDiagram;
        this.history = history;
        this.actionFactory = actionFactory;
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
            RevertibleNewClass revertibleNewClass = actionFactory.makeRevertibleNewClass(javaDiagram);
            history.forward(revertibleNewClass);
        };
    }
}
