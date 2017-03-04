package ch.ceruleansands.seshat.language;

import ch.ceruleansands.seshat.gui.ErgonomicMenuItem;
import ch.ceruleansands.seshat.io.loader.LanguageDiagramLoader;

/**
 * Defines what a language class should offer for each language's package.
 * @author Thomsch
 */
public interface Language {

    /**
     * Return the name of the language. For the Java programming language, this method returns "Java".
     * @return the name describing the language
     */
    String getName();

    /**
     * Return a menu item allowing the creation of a new diagram for this language.
     * @return The menu item allowing to create the new diagram.
     */
    ErgonomicMenuItem getNewDiagramAction();

    /**
     * Returns the diagram loader for this language.
     * @return The diagram loader to create diagrams for this language
     */
    LanguageDiagramLoader getLanguageDiagramLoader();
}
