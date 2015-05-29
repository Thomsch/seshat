package ch.ceruleansands.seshat.model;

/**
 * @author Thomas Schweizer.
 */
public class Type {
    private String value;

    public Type(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
