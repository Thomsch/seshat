package ch.ceruleansands.seshat.language;

import ch.ceruleansands.seshat.Language;
import ch.ceruleansands.seshat.language.java.Java;
import ch.ceruleansands.seshat.language.violet.Violet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Temporary class that is initializing statically the languages available for the editor
 * @author Thomsch
 */
@Singleton
public class StaticOmniscientInitializer implements LanguageInitializer {

    private final Set<Language> languages;
    private final Java java;
    private final Violet violet;

    @Inject
    public StaticOmniscientInitializer(Java java, Violet violet) {
        this.java = java;
        this.violet = violet;
        languages = new LinkedHashSet<>();
    }

    @Override
    public void loadLanguages() {
        languages.add(java);
        languages.add(violet);
    }

    @Override
    public Set<Language> getLanguages() {
        return languages;
    }
}
