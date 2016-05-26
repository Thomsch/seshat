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

package ch.ceruleansands.seshat.gui;

import ch.ceruleansands.seshat.language.java.gui.tile.OldTile;
import com.google.inject.Singleton;

import java.util.*;

/**
 * Holds the tiles selected on the interface.
 * @author Thomsch
 */
@Singleton
public class SelectionManager {

    Set<OldTile> selected;

    public SelectionManager() {
        selected = new HashSet<>();
    }

    public Collection<OldTile> getSelected() {
        return new HashSet<>(selected);
    }

    public void changeSelection(OldTile... newSelection) {
        this.selected.forEach(s -> s.setSelected(false));
        this.selected.clear();
        Collections.addAll(this.selected, newSelection);
        this.selected.forEach(s -> s.setSelected(true));
    }

    public void addToSelection(OldTile... newTiles) {
        Collections.addAll(this.selected, newTiles);
        Arrays.asList(newTiles).forEach(tile ->  tile.setSelected(true));
    }

    /**
     * Returns whether a tile is currently selected or not.
     * @param tile The tile checked.
     * @return True if it's selected. False otherwise.
     */
    public boolean isTileSelected(OldTile tile) {
        return selected.contains(tile);
    }

    /**
     * Removes tiles from selection.
     * @param tiles The tiles to remove from the selection.
     */
    public void removeFromSelection(OldTile... tiles) {
        List<OldTile> tileList = Arrays.asList(tiles);
        selected.removeAll(tileList);
        tileList.forEach(tile -> tile.setSelected(false));
    }
}
