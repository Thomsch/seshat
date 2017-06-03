package ch.ceruleansands.seshat.ui;

import ch.ceruleansands.seshat.component.editor.Editor;
import javafx.stage.Stage;

/**
 * @author Thomsch
 */
public interface GuiFactory {
    Editor makeEditor(Stage stage);
}
