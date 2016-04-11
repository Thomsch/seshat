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

package ch.ceruleansands.seshat.loader;

import ch.ceruleansands.seshat.Diagram;
import ch.ceruleansands.seshat.LanguageHandler;
import ch.ceruleansands.seshat.language.LanguageException;
import ch.ceruleansands.seshat.loader.header.Header;
import ch.ceruleansands.seshat.loader.header.HeaderException;
import ch.ceruleansands.seshat.loader.header.HeaderReader;
import com.google.common.io.Files;
import com.google.inject.Inject;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;

/**
 * Loads diagrams to be usable by the editor.
 * @author Thomsch.
 */
public class DiagramLoader {

    private HeaderReader headerReader;
    private LanguageHandler languageHandler;

    @Inject
    public DiagramLoader(HeaderReader headerReader, LanguageHandler languageHandler) {
        this.headerReader = headerReader;
        this.languageHandler = languageHandler;
    }

    /**
     * Loads a file from the hard drive. The file is selected by the user with the help of a GUI.
     * @return The file if something is selected. An empty optional otherwise.
     */
    public Optional<File> loadFileFromDisk(Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load a diagram");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        return Optional.ofNullable(fileChooser.showOpenDialog(window));
    }

    /**
     * Load a diagram from a file.
     * The format is the following:
     * - Seshat Project
     * - Last modified
     * - Language
     * - Language specific
     * @param file The file to read
     * @return The diagram ready to be used.
     * @throws FileNotFoundException when the <code>file</code> provided doesn't exist at the location
     * @throws IOException when the file cannot be closed after reading
     * @throws HeaderException when the header of the file is incorrect
     * @throws LanguageException when the diagram's language is not loaded into the editor
     */
    public Diagram loadDiagramFromFile(File file) throws FileNotFoundException, IOException, HeaderException, LanguageException {
        try (BufferedReader bufferedReader = Files.newReader(file, Charset.forName("utf-8"))) {
            Header header = headerReader.read(bufferedReader);

            if (languageHandler.isLanguageLoaded(header.getLanguageText())) {
                LanguageDiagramLoader diagramLoader = languageHandler.getDiagramLoaderForLanguage(header.getLanguageText());
                return diagramLoader.loadFromBuffer(bufferedReader);

            } else {
                throw LanguageException.languageNotLoaded(header.getLanguageText());
            }
        }
    }
}
