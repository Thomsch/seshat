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

package ch.ceruleansands.seshat.language.java;

import ch.ceruleansands.seshat.ClazzObserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Represent a java class.
 * @author Thomsch.
 */
public class ClazzData {

    private String name;
    private Collection<String> attributes;
    private Collection<String> methods;
    private Collection<ClazzObserver> observers;

    public ClazzData(String name, Collection<String> attributes, Collection<String> methods) {
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

    public ClazzData() {
        this(null, null, null);
    }

    public ClazzData(String name) {
        this(name, null, null);
    }

    public void addAttribute(String attribute) {
        attributes.add(attribute);
        observers.forEach(observer -> observer.onNewAttribute(attribute));
    }

    public void addMethod(String method) {
        attributes.add(method);
        observers.forEach(observer -> observer.onNewMethod(method));
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        observers.forEach(observer -> observer.onNameChanged(oldName, name));
    }

    public void addClazzObserver(ClazzObserver observer) {
        this.observers.add(observer);
    }

    public String getName() {
        return name;
    }

    public Collection<String> getAttributes() {
        return attributes;
    }

    public Collection<String> getMethods() {
        return methods;
    }
}
