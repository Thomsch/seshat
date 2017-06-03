package ch.ceruleansands.seshat.component.diagram;

import ch.ceruleansands.seshat.component.menu.MainMenu;
import ch.ceruleansands.seshat.ui.EditableLabel;
import javafx.scene.control.Tab;

import java.io.File;
import java.util.Optional;

/**
 * @author Thomsch.
 */
public class DiagramTab extends Tab{

    private final JavaDiagram diagram;
    private final MainMenu menuProxy;
    private final EditableLabel label;
    private Optional<File> file;

    public DiagramTab(String tabTitle, JavaDiagram diagram, MainMenu menuProxy) {
        super(null, diagram.getView());

        this.diagram = diagram;
        this.menuProxy = menuProxy;
        this.file = Optional.empty();

        label = new EditableLabel(tabTitle);
        setGraphic(label);
    }

    public void save() {
        file = diagram.save(file);
        file.ifPresent(c -> label.setDisplayText(c.getName()));
    }

    public void onUnfocused() {
        diagram.getEditItems().forEach(menuProxy::removeEditItem);
    }

    public void onFocused() {
        diagram.getEditItems().forEach(menuProxy::addEditItem);
    }
}
