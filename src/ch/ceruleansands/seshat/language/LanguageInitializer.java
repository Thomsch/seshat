package ch.ceruleansands.seshat.language;

import java.util.Set;

/**
 * @author Thomsch
 */
public interface LanguageInitializer {

    /**
     * Loads the languages that will be available for the editor.
     */
    void loadLanguages();

    /**
     * Returns all languages loaded.
     * @return All languages loaded.
     */
    Set<Language> getLanguages();
}
