package ch.ceruleansands.seshat.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Paints
 * @author Thomsch
 */
public class Background extends Canvas {

    private static final int gridSize = 30;

    public void draw(double tx, double ty) {
        double width = getWidth();
        double height = getHeight();

        GraphicsContext gc = getGraphicsContext2D();
        gc.save();

        gc.setFill(Color.DARKCYAN);
        gc.fillRect(0, 0, width, height);


        gc.setStroke(Color.WHITE);

        double x = tx % gridSize;
        while (x < width) {
            gc.strokeLine(x, 0, x, height);
            x += gridSize;
        }


        double y = ty % gridSize;
        while(y < height) {
            gc.strokeLine(0, y, width, y);
            y += gridSize;
        }

        gc.strokeText(String.format("(x:%s, y:%s)", tx, ty), 10, getHeight() - 15);
        gc.restore();
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }
}
