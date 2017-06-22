package ch.ceruleansands.seshat.component.tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * The model for a tile in a java diagram.
 * @author Thomsch
 */
public class TileModel {
    private String name;
    private double x;
    private double y;

    private Collection<String> attributes;
    private Collection<String> methods;
    private Collection<TileObserver> observers;

    public TileModel(String name, double x, double y, Collection<String> attributes, Collection<String> methods) {
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

    public TileModel(String name, double x, double y) {
        this(name, x, y, null, null);
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

    public void addTileObserver(TileObserver observer) {
        this.observers.add(observer);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}