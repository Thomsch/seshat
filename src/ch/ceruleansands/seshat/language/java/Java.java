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

package ch.ceruleansands.seshat.language.java;

import ch.ceruleansands.seshat.Language;
import ch.ceruleansands.seshat.gui.ErgonomicMenuItem;
import ch.ceruleansands.seshat.language.java.action.ActionFactory;
import ch.ceruleansands.seshat.loader.LanguageDiagramLoader;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Provides integration with the editor.
 * @author Thomsch
 */
public class Java implements Language {

    private final ActionFactory actionFactory;
    private Provider<DiagramLoader> diagramLoaderProvider;

    @Inject
    public Java(ActionFactory actionFactory, Provider<DiagramLoader> diagramLoaderProvider) {
        this.actionFactory = actionFactory;
        this.diagramLoaderProvider = diagramLoaderProvider;
    }

    @Override
    public String getName() {
        return "Java";
    }

    @Override
    public ErgonomicMenuItem getNewDiagramAction() {
        return actionFactory.makeNewDiagram();
    }

    @Override
    public LanguageDiagramLoader getLanguageDiagramLoader() {
        return diagramLoaderProvider.get();
    }
}
