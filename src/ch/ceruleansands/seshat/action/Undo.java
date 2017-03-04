package ch.ceruleansands.seshat.action;

import ch.ceruleansands.actionstream.ActionHistory;
import ch.ceruleansands.seshat.gui.ErgonomicMenuItem;
import com.google.inject.Inject;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * @author Thomsch
 */
public class Undo extends ErgonomicMenuItem {
    private final ActionHistory actionHistory;

    @Inject
    public Undo(ActionHistory actionHistory) {
        this.actionHistory = actionHistory;
    }

    @Override
    public String getTitle() {
        return "_Undo";
    }

    @Override
    public KeyCombination getAccelerator() {
        return new KeyCodeCombination(KeyCode.Z, KeyCodeCombination.CONTROL_DOWN);
    }

    @Override
    public EventHandler<ActionEvent> getAction() {
        return event -> actionHistory.undo();
    }

    @Override
    public ObservableValue<? extends Boolean> disableProperty() {
        return actionHistory.undoNotAvailableProperty();
    }
}
