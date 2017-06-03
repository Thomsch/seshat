package ch.ceruleansands.seshat.disabled.io;

import ch.ceruleansands.seshat.DialogHelper;
import ch.ceruleansands.seshat.component.diagram.JavaDiagram;
import ch.ceruleansands.seshat.component.editor.TabManager;
import ch.ceruleansands.seshat.component.menu.ErgonomicMenuItem;
import ch.ceruleansands.seshat.disabled.io.loader.DiagramLoader;
import ch.ceruleansands.seshat.disabled.io.loader.SaveFormatException;
import ch.ceruleansands.seshat.disabled.io.loader.header.HeaderException;
import com.google.inject.Inject;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

/**
 * @author Thomsch
 */
public class Load extends ErgonomicMenuItem {

    private final DiagramLoader diagramLoader;
    private final TabManager tabManager;
    private final DialogHelper dialogHelper;

    @Inject
    public Load(DiagramLoader diagramLoader, TabManager tabManager, DialogHelper dialogHelper) {
        this.diagramLoader = diagramLoader;
        this.tabManager = tabManager;
        this.dialogHelper = dialogHelper;
    }

    @Override
    public String getTitle() {
        return "_Load...";
    }

    @Override
    public KeyCombination getAccelerator() {
        return new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN);
    }

    @Override
    public EventHandler<ActionEvent> getAction() {
        return event -> {

            Optional<File> optFile = diagramLoader.loadFileFromDisk(tabManager.getTabPane().getScene().getWindow());
            optFile.ifPresent(file -> {
                System.out.println("Converting \"" + file.getName() + "\" at " + file.getAbsolutePath());
                try {
                    JavaDiagram diagram = diagramLoader.loadDiagramFromFile(file);
                    tabManager.addDiagram(diagram, file);
                } catch (FileNotFoundException e) {
                    dialogHelper.showError("File not found", e.getMessage());
                } catch (HeaderException e) {
                    dialogHelper.showError("The file header is incorrect", e.getMessage());
                } catch (IOException e) {
                    dialogHelper.showError("Failed to close the file", e.getMessage());
                } catch (SaveFormatException e) {
                    dialogHelper.showError("The format for this language is incorrect", e.getMessage());
                    e.printStackTrace();
                }
            });
        };
    }

    @Override
    public ObservableValue<? extends Boolean> disableProperty() {
        return new ReadOnlyBooleanWrapper(true);
    }
}
