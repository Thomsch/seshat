package ch.ceruleansands.seshat.language;

import ch.ceruleansands.seshat.gui.MenuProxy;
import ch.ceruleansands.seshat.io.loader.LanguageDiagramLoader;
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
