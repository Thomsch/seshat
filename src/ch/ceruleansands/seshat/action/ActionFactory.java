package ch.ceruleansands.seshat.action;

import ch.ceruleansands.seshat.component.diagram.Diagram;
import ch.ceruleansands.seshat.disabled.action.Redo;
import ch.ceruleansands.seshat.disabled.action.Undo;

/**
 * @author Thomsch
 */
public interface ActionFactory {

    Redo makeRedoAction();

    Undo makeUndoAction();

    NewClass makeNewClass(Diagram diagram);

    NewDiagram makeNewDiagram();
}
