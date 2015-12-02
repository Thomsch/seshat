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

import ch.ceruleansands.seshat.language.java.Clazz;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;

/**
 * Created by Thomsch.
 */
public class Tile extends Group{

    private final Clazz model;
    private Controller controller;
    private final View view;

    @Inject
    public Tile(@Assisted Clazz clazz, Controller controller) {
        view = new View(controller);
        model = clazz;

        model.addClazzObserver(view);
        view.setNewAttributeButtonAction(controller.newAttributeAction(clazz));
        view.setNewMethodButtonAction(controller.newMethodAction(clazz));
        view.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> controller.onSelection(event.isControlDown(), this));

        this.controller = controller;

        getChildren().add(view);
    }

    public void setSelected(boolean selected) {
        view.setSelected(selected);
    }
}
