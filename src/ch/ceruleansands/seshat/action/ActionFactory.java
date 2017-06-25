package ch.ceruleansands.seshat.action;

import ch.ceruleansands.seshat.component.diagram.Diagram;

/**
 * @author Thomsch
 */
public interface ActionFactory {

    NewClass makeNewClass(Diagram diagram);
}
