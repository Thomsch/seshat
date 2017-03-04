package ch.ceruleansands.seshat.gui;

import ch.ceruleansands.seshat.Editor;
import ch.ceruleansands.seshat.language.java.JavaDiagram;
import ch.ceruleansands.seshat.language.java.JavaTile;
import ch.ceruleansands.seshat.language.java.JavaTileModel;
import ch.ceruleansands.seshat.language.java.gui.tile.OldTile;
import javafx.stage.Stage;

/**
 * @author Thomsch
 */
public interface GuiFactory {
    OldTile makeTile(JavaTileModel clazzData, JavaDiagram diagram, JavaTile tile);

    Editor makeEditor(Stage stage);
}
