package ch.ceruleansands.seshat.action;

import ch.ceruleansands.seshat.Editor;
import ch.ceruleansands.seshat.gui.ErgonomicMenuItem;
import ch.ceruleansands.seshat.gui.TabManager;
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
public class Save extends ErgonomicMenuItem {

    private final TabManager tabManager;
    private Editor editor;

    @Inject
    public Save(TabManager tabManager) {
        this.tabManager = tabManager;
    }

    @Override
    public String getTitle() {
        return "_Save";
    }

    @Override
    public KeyCombination getAccelerator() {
        return new KeyCodeCombination(KeyCode.S, KeyCodeCombination.CONTROL_DOWN);
    }

    @Override
    public EventHandler<ActionEvent> getAction() {
        return event -> tabManager.saveCurrentDiagram();
    }

    @Override
    public ObservableValue<? extends Boolean> disableProperty() {
        return tabManager.unavailable();
    }
}
