package ch.ceruleansands.seshat.diagram;

import ch.ceruleansands.seshat.component.Relation;
import ch.ceruleansands.seshat.component.Tile;
import ch.ceruleansands.seshat.component.TileIdGenerator;
import ch.ceruleansands.seshat.gui.GuiFactory;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.util.Collection;

/**
 * Represents a java tile on the diagram
 * @author Thomsch
 */
public class JavaTile implements Tile{

    private final OldTile tile;
    private final JavaTileModel clazzData;

    @Inject
    public JavaTile(@Assisted JavaDiagram diagram, GuiFactory guiFactory, TileIdGenerator generator) {
        clazzData = new JavaTileModel(generator.getNext(), "Unnamed class", 0d,0d);
        tile = guiFactory.makeTile(clazzData, diagram, this);

        installContextMenu();
    }

    public void installContextMenu() {
        Node node = tile.getView();
        ContextMenu contextMenu = new ContextMenu(new MenuItem("New relation"));
        contextMenu.setAutoHide(true);

        node.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, event -> {
            contextMenu.show(node, event.getScreenX(), event.getScreenY());
            event.consume();
        });
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> contextMenu.hide());
    }

    public String getName() {
        return clazzData.getName();
    }

    public void setName(String name) {
        clazzData.setName(name);
    }

    public Collection<String> getAttributes() {
        return clazzData.getAttributes();
    }

    public Collection<String> getMethods() {
        return clazzData.getMethods();
    }

    @Override
    public Node getNode() {
        return tile.getView();
    }

    @Override
    public Relation getRelation() {
        return null;
    }

    public void highlightAnchors(boolean highlighted) {
        tile.highlightAnchors(highlighted);
    }
}
