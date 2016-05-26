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

package ch.ceruleansands.seshat;

import ch.ceruleansands.seshat.gui.GuiFactory;
import ch.ceruleansands.seshat.language.LanguageInitializer;
import ch.ceruleansands.seshat.language.java.JavaModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * I don't know what this class does yet. Still very much a prototype.
 * @author Thomas Schweizer.
 */
public class GuiLoader extends Application {

    private GuiFactory guiFactory;

    @Override
    public void init() throws Exception {
        Injector injector = Guice.createInjector(new Module(), new JavaModule());
        guiFactory = injector.getInstance(GuiFactory.class);

        // TODO Will be changed and put in a happy world where Guice and JavaFX live together in harmony.
        LanguageInitializer languageInitializer = injector.getInstance(LanguageInitializer.class);
        languageInitializer.loadLanguages();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Editor editor = guiFactory.makeEditor(stage);
        editor.configure();
        editor.show();
    }
}
