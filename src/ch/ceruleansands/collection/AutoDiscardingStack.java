/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 CeruleanSands
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ch.ceruleansands.collection;

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
    }

    /**
     * Retrieves and remove the top of the stack or null if empty.
     * @return the top of the stack, null if this stack is empty
     */
    public T pop() {
        return items.pollLast();
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

    @Override
    public String toString() {
        return items.toString();
    }
}
