/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 CeruleanSands
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ch.ceruleansands.seshat.language.java;

import ch.ceruleansands.seshat.GraphicData;
import ch.ceruleansands.seshat.TileIdGenerator;
import ch.ceruleansands.seshat.gui.GuiFactory;
import ch.ceruleansands.seshat.language.java.gui.tile.OldTile;
import ch.ceruleansands.seshat.tilediagram.Relation;
import ch.ceruleansands.seshat.tilediagram.Tile;
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
        clazzData = new JavaTileModel(generator.getNext(), "Unnamed class", new GraphicData(0d,0d));
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
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            contextMenu.hide();
        });
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
