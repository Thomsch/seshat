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
