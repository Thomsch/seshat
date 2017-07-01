package ch.thomsch.seshat.ui;

import ch.thomsch.seshat.component.tile.Tile;
import com.google.inject.Singleton;

import java.util.*;

/**
 * Holds the tiles selected on the interface.
 * @author Thomsch
 */
@Singleton
public class SelectionManager {

    Set<Tile> selected;

    public SelectionManager() {
        selected = new HashSet<>();
    }

    public Collection<Tile> getSelected() {
        return new HashSet<>(selected);
    }

    public void changeSelection(Tile... newSelection) {
        this.selected.forEach(s -> s.setSelected(false));
        this.selected.clear();
        Collections.addAll(this.selected, newSelection);
        this.selected.forEach(s -> s.setSelected(true));
    }

    public void addToSelection(Tile... newTiles) {
        Collections.addAll(this.selected, newTiles);
        Arrays.asList(newTiles).forEach(tile ->  tile.setSelected(true));
    }

    /**
     * Returns whether a tile is currently selected or not.
     * @param tile The tile checked.
     * @return True if it's selected. False otherwise.
     */
    public boolean isTileSelected(Tile tile) {
        return selected.contains(tile);
    }

    /**
     * Removes tiles from selection.
     * @param tiles The tiles to remove from the selection.
     */
    public void removeFromSelection(Tile... tiles) {
        List<Tile> tileList = Arrays.asList(tiles);
        selected.removeAll(tileList);
        tileList.forEach(tile -> tile.setSelected(false));
    }
}
