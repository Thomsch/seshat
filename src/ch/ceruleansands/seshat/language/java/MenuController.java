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

import ch.ceruleansands.seshat.Diagram;
import ch.ceruleansands.seshat.model.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Controller for the menus. This is where the business logic happens.
 * @author Thomsch
 */
public class MenuController {


    public EventHandler<ActionEvent> createNewClass(Model model) {
        return event -> model.addClass(new Clazz());
    }

    public EventHandler<ActionEvent> save(Diagram diagram) {
        return event -> {
            if (diagram != null) {
                Model model = diagram.getModel();
                File file = new File("test.ses");
                try {
                    PrintWriter writer = new PrintWriter(file);
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        System.out.println("Failed to create new file.");
                    }

                    model.getData().forEach(clazz -> {
                        writer.println(clazz.getName());
                        clazz.getAttributes().forEach(attribute -> {
                            writer.println("    " + attribute);
                        });

                        clazz.getMethods().forEach(method -> {
                            writer.println("    " + method);
                        });
                    });
                    writer.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("No diagram to save !");
            }
        };
    }
}
