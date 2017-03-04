package ch.ceruleansands.seshat.language.java.action;

import ch.ceruleansands.seshat.language.java.JavaDiagram;

/**
 * @author Thomsch
 */
public interface ActionFactory {
    NewClass makeNewClass(JavaDiagram javaDiagram);

    RevertibleNewClass makeRevertibleNewClass(JavaDiagram javaDiagram);

    NewDiagram makeNewDiagram();

    TestTile makeTestTile(JavaDiagram javaDiagram);
}
