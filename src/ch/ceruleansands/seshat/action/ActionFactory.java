package ch.ceruleansands.seshat.action;

/**
 * @author Thomsch
 */
public interface ActionFactory {

    Load makeLoadAction();

    Redo makeRedoAction();

    Save makeSaveAction();

    Undo makeUndoAction();
}
