package ch.ceruleansands.seshat.gui;

import ch.ceruleansands.seshat.component.Diagram;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.File;

/**
 * @author Thomsch
 */
@Singleton
public class TabManager implements ChangeListener<Tab> {

    private final TabPane tabPane;

    private final SimpleBooleanProperty isDiagramUnavailable;
    private final MenuProxy menuProxy;

    @Inject
    public TabManager(MenuProxy menuProxy) {
        this.menuProxy = menuProxy;
        tabPane = new TabPane();
        this.isDiagramUnavailable = new SimpleBooleanProperty(false);

        ReadOnlyObjectProperty<Tab> selectedItemProperty = tabPane.getSelectionModel().selectedItemProperty();
        selectedItemProperty.addListener(this);
    }

    /**
     * Adds an existing diagram to this editor and selects it.
     * @param diagram the diagram
     * @param file it's location
     */
    public void addDiagram(Diagram diagram, File file) {
        DiagramTab tab = new DiagramTab(file.getName(), diagram, menuProxy);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    /**
     * Adds an empty diagram to this editor.
     * @param empty the empty diagram
     */
    public void addDiagram(Diagram empty) {
        addDiagram(empty, new File("Unsaved diagram"));
    }

    public ObservableValue<? extends Boolean> unavailable() {
        return isDiagramUnavailable;
    }

    @Override
    public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
        if(oldValue != null) {
            DiagramTab oldDiagram = (DiagramTab) oldValue;
            oldDiagram.onUnfocused();
        }

        if(newValue != null) {
            DiagramTab newDiagram = (DiagramTab) newValue;
            newDiagram.onFocused();
        }

        isDiagramUnavailable.set(newValue == null);
    }

    public Node getTabPane() {
        return tabPane;
    }

    public void saveCurrentDiagram() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        DiagramTab selectedDiagram = (DiagramTab) selectedTab;

        selectedDiagram.save();
    }
}
