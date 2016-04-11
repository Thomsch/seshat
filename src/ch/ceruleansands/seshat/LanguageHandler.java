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

package ch.ceruleansands.seshat;

import ch.ceruleansands.seshat.gui.MenuProxy;
import ch.ceruleansands.seshat.language.LanguageInitializer;
import ch.ceruleansands.seshat.loader.LanguageDiagramLoader;
import com.google.inject.Inject;

import java.util.Objects;
import java.util.Optional;

/**
 * Handles operations related to language's modules.
 * @author Thomsch
 */
public class LanguageHandler {

    private final LanguageInitializer languageInitializer;

    @Inject
    public LanguageHandler(LanguageInitializer languageInitializer) {
        this.languageInitializer = languageInitializer;
    }

    /**
     * Adds menu items for each language installed to create a new diagram.
     * @param menu The menu where to add the menu items.
     */
    public void addNewDiagramOnMenu(MenuProxy menu) {
        languageInitializer.getLanguages().forEach(language -> menu.addFileItem(language.getNewDiagramAction()));
    }

    /**
     * Verifies if a language is loaded in this editor.
     * @param languageText The language name
     * @return <code>true</code> if one instance of the language is loaded. <code>false</code> otherwise
     */
    public boolean isLanguageLoaded(String languageText) {
        return languageInitializer.getLanguages().stream()
                                                    .filter(language -> languageText.equalsIgnoreCase(language.getName()))
                                                    .count() == 1;
    }

    /**
     * Returns the diagram loader for the specified language.
     * @param languageText The language name of the diagram loader we want.
     * @return The diagram loader for the specified language
     * @throws NullPointerException when <code>languageText</code> is null
     * @throws IllegalArgumentException when no language is loaded under the name <code>languageText</code>
     */
    public LanguageDiagramLoader getDiagramLoaderForLanguage(String languageText) {
        Objects.requireNonNull(languageText);
        Optional<Language> optLanguage = languageInitializer.getLanguages().stream().filter(language -> languageText.equalsIgnoreCase(language.getName())).findFirst();

        if (optLanguage.isPresent()) {
            return optLanguage.get().getLanguageDiagramLoader();
        } else {
            throw new IllegalArgumentException("No such language loaded");
        }
    }
}
