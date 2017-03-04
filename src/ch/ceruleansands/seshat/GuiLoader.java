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
