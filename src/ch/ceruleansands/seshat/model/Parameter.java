package ch.ceruleansands.seshat.model;

/**
 * @author Thomas Schweizer.
 */
public class Parameter {

    private String name;
    private Type type;

    public Parameter(Type type, String name) {
        this.type = type;
        this.name = name;
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
