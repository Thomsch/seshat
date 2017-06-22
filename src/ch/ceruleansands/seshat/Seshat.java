package ch.ceruleansands.seshat;

import ch.ceruleansands.seshat.component.editor.Editor;
import ch.ceruleansands.seshat.guice.Module;
import ch.ceruleansands.seshat.ui.GuiFactory;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Start point of the program.
 * @author Thomsch
 */
public class Seshat extends Application {

    private GuiFactory guiFactory;

    @Override
    public void init() throws Exception {
        Injector injector = Guice.createInjector(new Module());
        guiFactory = injector.getInstance(GuiFactory.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Editor editor = guiFactory.makeEditor(stage);
        editor.configure();
        editor.emptyDiagram();
        editor.show();
    }
}
