package ch.thomsch.seshat.action;

import ch.thomsch.seshat.component.diagram.Diagram;

/**
 * @author Thomsch
 */
public interface ActionFactory {

    NewClass makeNewClass(Diagram diagram);
}
