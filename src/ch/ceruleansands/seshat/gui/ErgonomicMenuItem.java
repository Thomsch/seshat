/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 CeruleanSands
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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