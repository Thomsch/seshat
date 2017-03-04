package ch.ceruleansands.seshat.component;

/**
 * @author Thomsch.
 */
public interface TileObserver {
    void onNameChanged(String oldName, String newName);

    void onNewAttribute(String attribute);

    void onNewMethod(String method);
}
