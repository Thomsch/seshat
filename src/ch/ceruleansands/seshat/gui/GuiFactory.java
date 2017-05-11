package ch.ceruleansands.seshat.gui;

import ch.ceruleansands.seshat.component.Editor;
import ch.ceruleansands.seshat.diagram.JavaDiagram;
import ch.ceruleansands.seshat.diagram.JavaTile;
import ch.ceruleansands.seshat.diagram.JavaTileModel;
import ch.ceruleansands.seshat.diagram.OldTile;
import javafx.stage.Stage;

/**
 * @author Thomsch
 */
public interface GuiFactory {
    OldTile makeTile(JavaTileModel clazzData, JavaDiagram diagram, JavaTile tile);

    Editor makeEditor(Stage stage);
}
