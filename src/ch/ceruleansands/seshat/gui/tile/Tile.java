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

import ch.ceruleansands.seshat.gui.ClazzModel;
import ch.ceruleansands.seshat.language.java.Clazz;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import javafx.scene.Group;

/**
 * Created by Thomsch.
 */
public class Tile extends Group{

    private final ClazzModel model;
    private Controller controller;

    @Inject
    public Tile(@Assisted Clazz clazz, Controller controller) {
        View view = new View(controller);
        model = new ClazzModel(clazz);

        this.controller = controller;

        getChildren().add(view);
    }

}
