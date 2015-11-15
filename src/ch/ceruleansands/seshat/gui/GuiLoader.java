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

package ch.ceruleansands.seshat.gui;

import ch.ceruleansands.seshat.language.java.Clazz;
import ch.ceruleansands.seshat.model.Model;
import ch.ceruleansands.seshat.model.Models;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * @author Thomas Schweizer.
 */
public class GuiLoader extends Application{

    private Group group;
    private Model model;

    private A a;
    private Label originLabel;

    public GuiLoader() {
        this.model = Models.createEmpty();
        model.addObserver(this);
    }

    @Override
    public void init() throws Exception {
        Injector injector = Guice.createInjector(new Module());
        a = injector.getInstance(A.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Seshat - V.0.0");

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("style.css");

        MenuBar menus = makeMenu();
        ToolBar toolBar = new ToolBar(new Button("hello"), new Label("sss"));

        originLabel = new Label("I'm the origin");

        Circle circle = new Circle(4, Color.MAGENTA);
        originLabel.setTranslateX(circle.getTranslateX());
        originLabel.setTranslateY(circle.getTranslateY()- 25);
        originLabel.setVisible(false);

        circle.setOnMouseEntered(event -> originLabel.setVisible(true));
        circle.setOnMouseExited(event -> originLabel.setVisible(false));

        group = new Group(circle, originLabel);
        Background background = new Background(group);
        Pane pane = new Pane(background, group);

        background.widthProperty().bind(pane.widthProperty());
        background.heightProperty().bind(pane.heightProperty());

        root.setCenter(pane);
        root.setRight(toolBar);
        root.setTop(menus);

        stage.setScene(scene);
//        stage.setMaximized(true);
        stage.show();
    }

    private MenuBar makeMenu() {
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("_File");

        menuFile.getItems().add(new MenuItem("_Save..."));
        menuFile.getItems().add(new MenuItem("_Load..."));
        Menu menuEdit = new Menu("_Edit");
        MenuItem menuItem = new MenuItem("_New class");
        menuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        menuItem.setOnAction(event -> model.addClass(new Clazz()));
        menuEdit.getItems().add(menuItem);
        Menu menuView = new Menu("Help");
        Menu menuDebug = new Menu("Debug");

        menuBar.getMenus().addAll(menuFile, menuEdit, menuView, menuDebug);
        return menuBar;
    }

    public void onNewClass(Clazz clazz) {
        Tile tile = new Tile();
        clazz.addObserver(tile);
        group.getChildren().addAll(tile);
    }
}
