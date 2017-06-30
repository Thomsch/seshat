package ch.ceruleansands.actionhistory;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Defines a auto discarding stack with a fixed size. When adding an element to a full stack, the oldest element will
 * be removed.
 * This collection isn't thread safe.
 * @author Thomsch
 */
public class AutoDiscardingStack<T> {

    private final long size;
    private final Deque<T> items;

    private final SimpleBooleanProperty emptyProperty;

    /**
     * Creates a new instance with the given fixed size.
     * @param size maximum size of the stack.
     * @throws IllegalArgumentException when the size is equal or below zero
     */
    public AutoDiscardingStack(long size) {
        this.size = size;
        if(this.size <= 0) {
            throw new IllegalArgumentException("Can't have an empty stack");
        }
        this.items = new ArrayDeque<>();
        this.emptyProperty = new SimpleBooleanProperty(true);
    }

    /**
     * Adds an item to this stack.
     * @param item the item to add
     * @throws IllegalArgumentException when <tt>item</tt> is <tt>null</tt>
     */
    public void push(T item) {
        if(item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if(items.size() == size) {
            items.removeFirst();
        }
        items.offerLast(item);
        updateEmptyProperty();
    }

    /**
     * Retrieves and remove the top of the stack or null if empty.
     * @return the top of the stack, null if this stack is empty
     */
    public T pop() {
        T item = items.pollLast();
        updateEmptyProperty();
        return item;
    }

    /**
     * Returns <tt>true</tt> if this stack contains no elements.
     * @return <tt>true</tt> if this stack contains no elements
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Removes all of the elements from this stack.
     */
    public void clear() {
        items.clear();
        updateEmptyProperty();
    }

    /**
     * Returns the current size of the stack.
     * @return the size of this stack
     */
    public int size() {
        return items.size();
    }

    /**
     * Returns the maximum size of the stack.
     * @return the maximum size of this stack
     */
    public long maxSize() {
        return size;
    }

    /**
     * Observable property to know if the stack is empty.
     * @return the empty property
     */
    public BooleanProperty emptyProperty() {
        return emptyProperty;
    }

    @Override
    public String toString() {
        return items.toString();
    }

    private void updateEmptyProperty() {
        emptyProperty.set(items.isEmpty());
    }
}
