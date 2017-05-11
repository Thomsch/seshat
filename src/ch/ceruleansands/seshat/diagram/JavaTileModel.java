package ch.ceruleansands.seshat.diagram;

import ch.ceruleansands.seshat.component.TileObserver;

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
