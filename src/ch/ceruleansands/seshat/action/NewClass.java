package ch.ceruleansands.seshat.action;

import ch.ceruleansands.seshat.component.diagram.JavaDiagram;
import ch.ceruleansands.seshat.component.menu.ErgonomicMenuItem;
import ch.ceruleansands.seshat.component.tile.TileModel;
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
    private final ActionFactory actionFactory;

    /**
     * Creates a new instance of a newClassAction.
     */
    @Inject
    public NewClass(@Assisted JavaDiagram javaDiagram, ActionFactory actionFactory) {
        this.javaDiagram = javaDiagram;
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
            javaDiagram.addTile(new TileModel("Unamed tile", javaDiagram.getMousePos().getX(), javaDiagram.getMousePos().getY()));
        };
    }
}
