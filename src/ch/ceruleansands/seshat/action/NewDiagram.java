package ch.ceruleansands.seshat.action;

import ch.ceruleansands.seshat.component.diagram.DiagramBuilder;
import ch.ceruleansands.seshat.component.editor.TabManager;
import ch.ceruleansands.seshat.component.menu.ErgonomicMenuItem;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * @author Thomsch
 */
public class NewDiagram extends ErgonomicMenuItem {
    private final TabManager tabManager;
    private final DiagramBuilder builder;

    @Inject
    public NewDiagram(TabManager tabManager, DiagramBuilder builder) {
        this.tabManager = tabManager;
        this.builder = builder;
    }

    @Override
    public String getTitle() {
        return "_New java diagram";
    }

    @Override
    public KeyCombination getAccelerator() {
        return new KeyCodeCombination(KeyCode.J, KeyCodeCombination.CONTROL_DOWN);
    }

    @Override
    public EventHandler<ActionEvent> getAction() {
        return event -> tabManager.addDiagram(builder.createEmpty());
    }
}