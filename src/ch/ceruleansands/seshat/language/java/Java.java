package ch.ceruleansands.seshat.language.java;

import ch.ceruleansands.seshat.gui.ErgonomicMenuItem;
import ch.ceruleansands.seshat.io.loader.LanguageDiagramLoader;
import ch.ceruleansands.seshat.language.Language;
import ch.ceruleansands.seshat.language.java.action.ActionFactory;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Provides integration with the editor.
 * @author Thomsch
 */
public class Java implements Language {

    private final ActionFactory actionFactory;
    private Provider<SAXLoader> diagramLoaderProvider;

    @Inject
    public Java(ActionFactory actionFactory, Provider<SAXLoader> diagramLoaderProvider) {
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
