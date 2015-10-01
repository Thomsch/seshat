package ch.ceruleansands.seshat.model;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Thomas Schweizer.
 */
public class Clazz {

    private Visibility visibility;
    private List<ClazzMethod> methods;
    private List<Attribute> attributes;
    private String name;

    public Clazz(String name) {
        methods = new LinkedList<>();
        attributes = new LinkedList<>();
        this.name = name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ClazzMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<ClazzMethod> methods) {
        this.methods = methods;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }
}
