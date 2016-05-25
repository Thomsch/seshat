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

import ch.ceruleansands.seshat.TileObserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * The model for a tile in a java diagram.
 * @author Thomsch
 */
public class JavaTileModel {
    private final String id;
    private String name;
    private double x;
    private double y;

    private Collection<String> attributes;
    private Collection<String> methods;
    private Collection<TileObserver> observers;

    public JavaTileModel(String id, String name, double x, double y, Collection<String> attributes, Collection<String> methods) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.attributes = new ArrayList<>();
        this.methods = new ArrayList<>();
        this.name = name;
        observers = new HashSet<>();

        if (attributes != null) {
            this.attributes.addAll(attributes);
        }

        if (methods != null) {
            this.methods.addAll(methods);
        }
    }

    public JavaTileModel(String id, String name, double x, double y) {
        this(id, name, x, y, null, null);
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        observers.forEach(observer -> observer.onNameChanged(oldName, name));
    }

    public String getName() {
        return name;
    }

    public void addAttribute(String attribute) {
        attributes.add(attribute);
        observers.forEach(observer -> observer.onNewAttribute(attribute));
    }

    public Collection<String> getAttributes() {
        return attributes;
    }

    public void addMethod(String method) {
        attributes.add(method);
        observers.forEach(observer -> observer.onNewMethod(method));
    }

    public Collection<String> getMethods() {
        return methods;
    }

    public void addClazzObserver(TileObserver observer) {
        this.observers.add(observer);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
