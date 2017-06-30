package ch.thomsch.seshat.component.tile;

/**
 * @author Thomsch.
 */
public interface TileObserver {
    void onNameChanged(String oldName, String newName);

    void onNewAttribute(String attribute);

    void onNewMethod(String method);
}
