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

import ch.ceruleansands.seshat.gui.GuiFactory;
import ch.ceruleansands.seshat.language.java.gui.tile.OldTile;
import ch.ceruleansands.seshat.tilediagram.Relation;
import ch.ceruleansands.seshat.tilediagram.Tile;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;

import java.util.Collection;

/**
 * @author Thomsch
 */
public class JavaTile implements Tile{

    private final OldTile tile;
    private final ClazzData clazzData;

    @Inject
    public JavaTile(GuiFactory guiFactory) {
        clazzData = new ClazzData("Undefined class");
        tile = guiFactory.makeTile(clazzData);

        Node node = tile.getView();
        ContextMenu contextMenu = new ContextMenu(new MenuItem("Hallo"));
        node.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.SECONDARY) {
                System.out.println("B");
                contextMenu.show(node, event.getScreenX(), event.getScreenY());
                event.consume();
            }
        });
    }

    public Node getView() {
        return tile.getView();
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
        return getView();
    }

    @Override
    public Relation getRelation() {
        return null;
    }
}
