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

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SeparatorMenuItem;

/**
 * Proxy to interact with the structure of the menus for this JavaFx GUI.
 * @author Thomsch
 */

@Singleton
public class MenuProxy {
    private MenuBar menuBar;
    private final Menu menuFile;
    private final Menu menuEdit;
    private final Menu menuHelp;

    @Inject
    public MenuProxy() {
        menuBar = new MenuBar();
        menuFile = new Menu("_File");
        menuEdit = new Menu("_Edit");
        menuHelp = new Menu("_Help");
        menuBar.getMenus().addAll(menuFile, menuEdit, menuHelp);
    }

    /**
     * Returns the JavaFx element representing the menus.
     * @return
     */
    public MenuBar getMenuBar() {
        return menuBar;
    }

    public void addFileItem(ErgonomicMenuItem item) {
        addItem(menuFile, item);
    }

    public void addEditItem(ErgonomicMenuItem item) {
        addItem(menuEdit, item);
    }

    public void addHelpItem(ErgonomicMenuItem item) {
        addItem(menuHelp, item);
    }

    public void addFileSeparator() {
        addSeparator(menuFile);
    }

    public void addEditSeparator() {
        addSeparator(menuEdit);
    }

    public void addHelpSeparator() {
        addSeparator(menuHelp);
    }

    public void addSeparator(Menu menu) {
        menu.getItems().add(new SeparatorMenuItem());
    }

    public void addItem(Menu menu, ErgonomicMenuItem item) {
        menu.getItems().add(item.getAsMenuItem());
    }

    public void removeEditItem(ErgonomicMenuItem item) {
        removeItem(menuEdit, item);
    }

    private void removeItem(Menu menu, ErgonomicMenuItem item) {
        menu.getItems().remove(item.getAsMenuItem());
    }
}
