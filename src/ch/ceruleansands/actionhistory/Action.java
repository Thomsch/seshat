package ch.ceruleansands.actionhistory;

/**
 * @author Thomsch.
 */
public interface Action {

    void execute();

    void revert();
}
