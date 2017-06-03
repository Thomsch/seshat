package ch.ceruleansands.seshat.disabled.action;

import ch.ceruleansands.actionstream.ActionHistory;
import ch.ceruleansands.seshat.component.menu.ErgonomicMenuItem;
import com.google.inject.Inject;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * @author Thomsch
 */
public class Redo extends ErgonomicMenuItem {

    private final ActionHistory actionHistory;

    @Inject
    public Redo(ActionHistory actionHistory) {
        this.actionHistory = actionHistory;
    }

    @Override
    public String getTitle() {
        return "_Redo";
    }

    @Override
    public KeyCombination getAccelerator() {
        return new KeyCodeCombination(KeyCode.Z, KeyCodeCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
    }

    @Override
    public EventHandler<ActionEvent> getAction() {
        return event -> actionHistory.redo();
    }

    @Override
    public ObservableValue<? extends Boolean> disableProperty() {
        return new ReadOnlyBooleanWrapper(true);
    }
}
