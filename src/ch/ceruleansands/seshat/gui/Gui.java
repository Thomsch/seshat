package ch.ceruleansands.seshat.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Thomas Schweizer.
 */
public class Gui extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Seshat - V.0.0");

        MenuBar menus = makeMenu();

        BorderPane root = new BorderPane();
        root.setTop(menus);
        Text message = new Text(":::");
        message.setFont(new Font(380));
        message.setFill(Color.SANDYBROWN);
        root.setCenter(message);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private MenuBar makeMenu() {
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("Help");

        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        return menuBar;
    }

    public void start() {
        launch();
    }
}
