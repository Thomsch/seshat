/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 CeruleanSands
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
