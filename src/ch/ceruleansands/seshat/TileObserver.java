package ch.ceruleansands.seshat;

/**
 * @author Thomsch.
 */
public interface TileObserver {
    void onNameChanged(String oldName, String newName);

    void onNewAttribute(String attribute);

    void onNewMethod(String method);
}
