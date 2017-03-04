package ch.ceruleansands.seshat.gui;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

/**
 * @author Thomsch
 */
public abstract class ErgonomicMenuItem {

    private MenuItem menuItem;

    public abstract String getTitle();

    public abstract KeyCombination getAccelerator();

    public abstract EventHandler<ActionEvent> getAction();

    /**
     * The property indicating if this menu item should be disabled or not.
     * By default, the menu item is not disabled.
     * @return the property indicating whether it should be disabled
     */
    public ObservableValue<? extends Boolean> disableProperty() {
        return new SimpleBooleanProperty(false);
    }

    public MenuItem getAsMenuItem() {
        if(menuItem == null) {
            menuItem = new MenuItem(getTitle());
            menuItem.setAccelerator(getAccelerator());
            menuItem.setOnAction(getAction());
            menuItem.disableProperty().bind(disableProperty());
        }
        return menuItem;
    }
}
