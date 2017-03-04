package ch.ceruleansands.actionstream;

/**
 * @author Thomsch.
 */
public interface Action {

    void execute();

    void revert();
}
