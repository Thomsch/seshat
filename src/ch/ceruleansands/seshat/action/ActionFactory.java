package ch.ceruleansands.seshat.action;

import ch.ceruleansands.seshat.component.diagram.Diagram;
import ch.ceruleansands.seshat.disabled.action.Redo;
import ch.ceruleansands.seshat.disabled.action.Undo;
import ch.ceruleansands.seshat.disabled.io.Load;
import ch.ceruleansands.seshat.disabled.io.Save;

/**
 * @author Thomsch
 */
public interface ActionFactory {

    Load makeLoadAction();

    Redo makeRedoAction();

    Save makeSaveAction();

    Undo makeUndoAction();

    NewClass makeNewClass(Diagram diagram);

    NewDiagram makeNewDiagram();
}
