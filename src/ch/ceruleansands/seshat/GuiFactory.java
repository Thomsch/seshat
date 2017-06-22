package ch.ceruleansands.seshat;

import ch.ceruleansands.seshat.component.editor.Editor;
import javafx.scene.layout.BorderPane;

/**
 * @author Thomsch
 */
public interface GuiFactory {
    Editor createEditor(BorderPane view);
}
