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
import ch.ceruleansands.seshat.language.java.JavaDiagram;
import ch.ceruleansands.seshat.language.java.JavaTile;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * @author Thomsch
 */
public class RevertibleNewClass implements Action {
    private final JavaDiagram diagram;
    private final JavaTile tile;

    /**
     * Creates a new instance of a newClassAction.
     */
    @Inject
    public RevertibleNewClass(@Assisted JavaDiagram diagram, JavaTile tile) {
        this.diagram = diagram;
        this.tile = tile;
    }

    @Override
    public void execute() {
        try {
            diagram.addTile(tile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void revert() {
        diagram.removeTile(tile);
    }
}