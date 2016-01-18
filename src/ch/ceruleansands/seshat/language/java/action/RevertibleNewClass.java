/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 CeruleanSands
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

package ch.ceruleansands.seshat.language.java.action;

import ch.ceruleansands.actionstream.Action;
import ch.ceruleansands.seshat.language.java.JavaTile;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import javafx.scene.Group;

import java.util.Collection;

/**
 * @author Thomsch
 */
public class RevertibleNewClass implements Action {
    private Collection<JavaTile> tiles;
    private final Group elements;
    private final JavaTile tile;

    /**
     * Creates a new instance of a newClassAction.
     *
     * @param tiles    The list of tiles currently on the model.
     * @param elements The GUI element containing the tiles.
     */
    @Inject
    public RevertibleNewClass(@Assisted Collection<JavaTile> tiles, @Assisted Group elements, JavaTile tile) {
        this.tiles = tiles;
        this.elements = elements;
        this.tile = tile;
    }

    @Override
    public void execute() {
        try {
            tiles.add(tile);
            elements.getChildren().add(tile.getView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void revert() {
        tiles.remove(tile);
        elements.getChildren().remove(tile.getView());
    }
}
