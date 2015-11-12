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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private Button newTile;
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
        originLabel = new Label("I'm the origin");

        Circle circle = new Circle(4, Color.MAGENTA);
        originLabel.setTranslateX(circle.getTranslateX());
        originLabel.setTranslateY(circle.getTranslateY()- 25);
        originLabel.setVisible(false);

        circle.setOnMouseEntered(event -> originLabel.setVisible(true));

        circle.setOnMouseExited(event -> originLabel.setVisible(false));

        group = new Group(circle, originLabel);
        newTile = new Button("New tile");
        Background background = new Background(group);
        Pane pane = new Pane(background, group, newTile);

        background.widthProperty().bind(pane.widthProperty());
        background.heightProperty().bind(pane.heightProperty());

        root.setCenter(pane);
        root.setTop(menus);

        newTile.setDefaultButton(true);
        newTile.setOnAction(event -> {
            model.addClass(new Clazz());
        });
        stage.setScene(scene);
//        stage.setMaximized(true);
        stage.show();
        System.out.println("hlle");

        //        gui.addNewTileEvent(event -> model.addClass(new Clazz()));
//        Model model = Models.createEmpty();
//        model.addObserver(gui);
//
////        Clazz first = new Clazz();
////        first.addAttribute("int abc");
////        first.addAttribute("int cba");
////        first.addMethod("public void test()");
////
////        model.addClass(first);
//
    }

    public void addNewTileEvent(EventHandler<ActionEvent> actionEvent) {
        newTile.setOnAction(actionEvent);
    }

    private MenuBar makeMenu() {
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        menuFile.getItems().add(new MenuItem("Save"));
        menuFile.getItems().add(new MenuItem("Load"));
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("Help");
        Menu menuDebug = new Menu("Debug");

        menuBar.getMenus().addAll(menuFile, menuEdit, menuView, menuDebug);
        return menuBar;
    }

//    private Node makeDraggable(final Node node) {
//        final DragContext dragContext = new DragContext();
//        final Group wrapGroup = new Group(node);
//
////        wrapGroup.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
////            public void handle(final MouseEvent mouseEvent) {
////                if (dragModeActiveProperty.get()) {
////                    // disable mouse events for all children
//////                    mouseEvent.consume();
////                }
////            }
////        });
//
//        wrapGroup.addEventFilter(MouseEvent.MOUSE_PRESSED,
//                new EventHandler<MouseEvent>() {
//                    public void handle(final MouseEvent mouseEvent) {
//                            // remember initial mouse cursor coordinates
//                            // and node position
//                            dragContext.mouseAnchorX = mouseEvent.getX();
//                            dragContext.mouseAnchorY = mouseEvent.getY();
//                            dragContext.initialTranslateX = node.getTranslateX();
//                            dragContext.initialTranslateY = node.getTranslateY();
//                    }
//                });
//
//        wrapGroup.addEventFilter(MouseEvent.MOUSE_DRAGGED,
//                new EventHandler<MouseEvent>() {
//                    public void handle(final MouseEvent mouseEvent) {
//                            // shift node from its initial position by delta
//                            // calculated from mouse cursor movement
//                            node.setTranslateX(dragContext.initialTranslateX
//                                    + mouseEvent.getX() - dragContext.mouseAnchorX);
//                            node.setTranslateY(dragContext.initialTranslateY
//                                    + mouseEvent.getY() - dragContext.mouseAnchorY);
//                    }
//
//                });
//        return wrapGroup;
//    }

    public void onNewClass(Clazz clazz) {
        Tile tile = new Tile();
        clazz.addObserver(tile);
        group.getChildren().addAll(tile);
    }
}
