package ch.ceruleansands.seshat.model;

/**
 * @author Thomas Schweizer.
 */
public class Attribute {

    private Visibility visibility;
    private Type type;
    private String name;

    public Attribute(Visibility visibility, Type type, String name) {
        this.visibility = visibility;
        this.type = type;
        this.name = name;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
