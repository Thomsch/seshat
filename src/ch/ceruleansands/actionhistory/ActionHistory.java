package ch.ceruleansands.actionhistory;

import com.google.inject.Inject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Stack;

/**
 * Represents all the actions done until now.
 * This class allows to execute, undo and redo actions.
 * @author Thomsch.
 */
public class ActionHistory {

    private final Logger logger = LoggerFactory.getLogger(ActionHistory.class);

    private final AutoDiscardingStack<Action> actions;
    private final Stack<Action> rewindedActions;

    private final BooleanProperty rewindAvailable;

    /**
     * Creates a new instance with a given maximum length.
     * @param size the length of the history we went to be able to trace.
     * @throws IllegalArgumentException if {@code size} below or equals to zero
     */
    @Inject
    public ActionHistory(int size) {
        this.actions = new AutoDiscardingStack<>(size);
        this.rewindedActions = new Stack<>();
        this.rewindAvailable = new SimpleBooleanProperty(false);
    }

    /**
     * Executes <tt>action</tt> and then saves it so it can be undone later.
     * @param action The action to execute
     * @throws NullPointerException if {@code action} is {@code null}
     */
    public void forward(Action action) {
        Objects.requireNonNull(action);
        executeAndStoreAction(action);
        rewindedActions.clear();
        updateRewindAvailableProperty();
    }

    /**
     * Adds an action to the history but doesn't executes it. Be sure to execute it on your side.
     * @param action the action to add on the history stack.
     */
    public void add(Action action) {
        actions.push(action);
        rewindedActions.clear();
        updateRewindAvailableProperty();
    }

    /**
     * Redoes the last action that has been undone.
     */
    public void redo() {
        if (!rewindedActions.empty()) {
            Action toRedo = rewindedActions.pop();
            executeAndStoreAction(toRedo);
            updateRewindAvailableProperty();
        }
    }

    /**
     * Undoes the last action executed, either by {@link #redo()} or {@link #forward(Action)}.
     */
    public void undo() {
        Action lastAction = actions.pop();
        if (lastAction != null) {
            try {
                lastAction.revert();
                rewindedActions.push(lastAction);
                updateRewindAvailableProperty();
            } catch (Throwable t) {
                logger.error("The action {} threw an unhandled exception while being reverted. It won't be saved on the action's stack.", lastAction);
            }
        }
    }

    public BooleanProperty redoAvailableProperty() {
        return rewindAvailable;
    }

    public BooleanProperty undoNotAvailableProperty() {
        return actions.emptyProperty();
    }

    private void executeAndStoreAction(Action action) {
        try {
            action.execute();
            actions.push(action);
        } catch (Throwable t) {
            logger.error("The action {} threw an unhandled exception while being executed. It won't be saved on the action's stack.", action);
        }
    }

    private void updateRewindAvailableProperty() {
        rewindAvailable.set(!rewindedActions.empty());
    }
}
