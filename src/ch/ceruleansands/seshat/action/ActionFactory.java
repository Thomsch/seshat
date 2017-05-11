package ch.ceruleansands.seshat.action;

import ch.ceruleansands.seshat.diagram.JavaDiagram;

/**
 * @author Thomsch
 */
public interface ActionFactory {

    Load makeLoadAction();

    Redo makeRedoAction();

    Save makeSaveAction();

    Undo makeUndoAction();

    NewClass makeNewClass(JavaDiagram javaDiagram);

    RevertibleNewClass makeRevertibleNewClass(JavaDiagram javaDiagram);

    NewDiagram makeNewDiagram();

    TestTile makeTestTile(JavaDiagram javaDiagram);
}
