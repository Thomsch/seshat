package ch.thomsch.actionhistory;

/**
 * @author Thomsch.
 */
public interface Action {

    void execute();

    void revert();
}
