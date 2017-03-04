package ch.ceruleansands.seshat.language.java;

/**
 * @author Thomsch
 */
class DragInfo {
    private double xdraginit;
    private double ydraginit;
    private boolean dragged;

    public void setInit(double x, double y) {
        xdraginit = x;
        ydraginit = y;
    }

    public void setDragged(double currentX, double currentY) {
        dragged = !(xdraginit == currentX && ydraginit == currentY);
    }

    public boolean isDragged() {
        return dragged;
    }
}
