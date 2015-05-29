package ch.ceruleansands.seshat.model;

import java.util.List;

/**
 * @author Thomas Schweizer.
 */
public class Method {

    private String name;

    private List<Parameter> parameters;

    private Type returnType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }
}
