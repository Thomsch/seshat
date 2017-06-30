package ch.thomsch.seshat;

import ch.thomsch.seshat.component.editor.Editor;
import ch.thomsch.seshat.guice.Module;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Start point for the application.
 * It configures and shows the GUI after having bootstrapped Guice.
 * @author Thomsch
 */
public class Seshat extends Application {
    private static final double WIDTH = 1000;
    private static final double GOLDEN_RATIO = 1.6180339887;
    private static final double HEIGHT = WIDTH / GOLDEN_RATIO;

    private Editor editor;

    @Override
    public void init() throws Exception {
        final Injector injector = Guice.createInjector(new Module());
        editor = injector.getInstance(Editor.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Seshat");
        configureIcons(stage);

        final Parent root = configureEditor();
        final Scene scene = buildScene(root);

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    /**
     * @return the the UI pane on which the editor operates.
     */
    private Parent configureEditor() {
        final Parent root = editor.createView();
        editor.setEmptyDiagram();
        return root;
    }

    private Scene buildScene(Parent root) {
        final Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add("style.css");
        return scene;
    }

    private void configureIcons(Stage stage) {
        stage.getIcons().addAll(
                new Image("icon/16.png"),
                new Image("icon/24.png"),
                new Image("icon/32.png"),
                new Image("icon/48.png"),
                new Image("icon/128.png"));
    }
}
