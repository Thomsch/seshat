package ch.ceruleansands.seshat.action;

import ch.ceruleansands.seshat.Diagram;
import ch.ceruleansands.seshat.gui.DialogHelper;
import ch.ceruleansands.seshat.gui.ErgonomicMenuItem;
import ch.ceruleansands.seshat.gui.TabManager;
import ch.ceruleansands.seshat.language.LanguageException;
import ch.ceruleansands.seshat.language.java.SaveFormatException;
import ch.ceruleansands.seshat.loader.DiagramLoader;
import ch.ceruleansands.seshat.loader.header.HeaderException;
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
                    Diagram diagram = diagramLoader.loadDiagramFromFile(file);
                    tabManager.addDiagram(diagram, file);
                } catch (FileNotFoundException e) {
                    dialogHelper.showError("File not found", e.getMessage());
                } catch (HeaderException e) {
                    dialogHelper.showError("The file header is incorrect", e.getMessage());
                } catch (LanguageException e) {
                    dialogHelper.showError("The target language is not loaded", e.getMessage());
                } catch (IOException e) {
                    dialogHelper.showError("Failed to close the file", e.getMessage());
                } catch (SaveFormatException e) {
                    dialogHelper.showError("The format for this language is incorrect", e.getMessage());
                    e.printStackTrace();
                }
            });
        };
    };

    @Override
    public ObservableValue<? extends Boolean> disableProperty() {
        return new ReadOnlyBooleanWrapper(false);
    }
}
