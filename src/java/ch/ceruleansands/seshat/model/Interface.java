package ch.ceruleansands.seshat.model;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Thomas Schweizer.
 */
public class Interface {
    private List<Method> methods;

    public Interface() {
        methods = new LinkedList<>();
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }
}
