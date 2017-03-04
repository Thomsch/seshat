package ch.ceruleansands.seshat.language.java;

/**
 * Represents the amount of x and y translation from the origin point (0,0).
 */
public class TranslationTracker {

    // The translation difference from the initial dragging point.
    private double xShift = 0;
    private double yShift = 0;

    // The initial point where the drag happened.
    private double xInit;
    private double yInit;

    private double xtranslate = 0;
    private double ytranslate = 0;

    /**
     * Initialize a drag event.
     * @param x the x coordinate were the drag started
     * @param y the y coordinate were the drag started
     */
    public void init(double x, double y) {
        xInit = x;
        yInit = y;
        xShift = 0;
        yShift = 0;
    }

    /**
     * Sets the new x and y coordinate of the drag event.
     * @param x the current x coordinate of the drag
     * @param y the current y coordinate of the drag
     */
    public void update(double x, double y) {
        xShift = xInit - x;
        yShift = yInit - y;
    }

    /**
     * Returns the total translated distance for x coordinate until now.
     * @return the total translated distance until now
     */
    public double getAbsoluteXTranslation() {
        return absoluteTranslation(xtranslate, xShift);
    }

    /**
     * Returns the total translated distance for y coordinate until now.
     * @return the total translated distance until now
     */
    public double getAbsoluteYTranslation() {
        return absoluteTranslation(ytranslate, yShift);
    }

    private double absoluteTranslation(double translate, double tempTranslate) {
        return -(translate + tempTranslate);
    }

    /**
     * Stops the dragging event, by adding all the drag distance to the total drag  distance.
     */
    public void end() {
        xtranslate += xShift;
        ytranslate += yShift;
    }

    public double getXTranslate() {
        return xtranslate;
    }

    public double getYTranslate() {
        return ytranslate;
    }
}
