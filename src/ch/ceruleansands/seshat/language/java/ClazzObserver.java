package ch.ceruleansands.seshat.language.java;

/**
 * @author Thomsch.
 */
public interface ClazzObserver {
    void onNameChanged(String oldName, String newName);

    void onNewAttribute(String attribute);

    void onNewMethod(String method);
}
