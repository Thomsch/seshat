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

package ch.ceruleansands.seshat.gui.tile;

import ch.ceruleansands.seshat.gui.SelectionManager;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * @author Thomsch
 */
class Controller {

    private final SelectionManager selectionManager;
    private Tile tile;

    @Inject
    public Controller(SelectionManager selectionManager) {
        this.selectionManager = selectionManager;
    }

    public void install(Tile tile) {
        this.tile = tile;
    }

    public EventHandler<ActionEvent> newAttributeAction() {
        return null;
    }

    public EventHandler<ActionEvent> newMethodAction() {
        return null;
    }

    public void onSelection(MouseEvent event) {
        if (event.isControlDown()) {
            selectionManager.addToSelection(tile);
        } else {
            selectionManager.changeSelection(tile);
        }
    }
}
