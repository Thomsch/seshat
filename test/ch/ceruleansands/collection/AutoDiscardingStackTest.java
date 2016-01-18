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

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Thomsch
 */
public class AutoDiscardingStackTest {

    @Test(expected = IllegalArgumentException.class)
    public void constructor_ShouldThrowExceptionIfSizeIsZero() throws Exception {
        new AutoDiscardingStack(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_ShouldThrowExceptionIfSizeIsBelowZero() throws Exception {
        new AutoDiscardingStack(-1);
    }

    @Test
    public void constructor_ShouldBeEmptyWithNewInstance() throws Exception {
        AutoDiscardingStack stack = new AutoDiscardingStack(1);
        assertTrue(stack.isEmpty());
    }

    @Test
    public void push_ShouldMakeStackNotEmpty() throws Exception {
        AutoDiscardingStack<Object> stack = new AutoDiscardingStack<>(1);

        stack.push(new Object());

        assertFalse(stack.isEmpty());
        assertEquals(1, stack.size());
    }

    @Test
    public void push_ShouldReplaceOlderElementWhenFull() throws Exception {
        AutoDiscardingStack<Integer> stack = new AutoDiscardingStack<>(1);

        stack.push(1);
        stack.push(2);

        assertEquals(1, stack.size());
        assertEquals(2, (int) stack.pop());
    }

    @Test
    public void push_ShouldThrowIllegalArgumentExceptionIfNullItem() throws Exception {
        AutoDiscardingStack<Integer> stack = new AutoDiscardingStack<>(1);

        try {
            stack.push(null);
        } catch (IllegalArgumentException ex) {
            return;
        }
        fail();
    }

    @Test
    public void push_ShouldIncreaseStackSizeIfRoom() throws Exception {
        AutoDiscardingStack<Object> stack = new AutoDiscardingStack<>(2);

        stack.push(new Object());

        assertEquals(1, stack.size());

        stack.push(new Object());

        assertEquals(2, stack.size());

        stack.push(new Object());

        assertEquals(2, stack.size());
    }

    @Test
    public void clear_ShouldRemoveAllItems() throws Exception {
        AutoDiscardingStack<Object> stack = new AutoDiscardingStack<>(2);

        stack.push(new Object());
        stack.push(new Object());

        stack.clear();

        assertEquals(0, stack.size());
        assertNull(stack.pop());
    }

    @Test
    public void clear_ShouldDoNothingIfStackIsEmpty() throws Exception {
        AutoDiscardingStack<Object> stack = new AutoDiscardingStack<>(2);

        stack.clear();

        assertEquals(0, stack.size());
        assertNull(stack.pop());
    }

    @Test
    public void size_ShouldCountNumberOfItems() throws Exception {
        AutoDiscardingStack<Object> stack = new AutoDiscardingStack<>(2);

        stack.push(new Object());
        stack.push(new Object());
        stack.pop();
        stack.push(new Object());

        assertEquals(2, stack.size());
    }

    @Test
    public void pop_ShouldRetrieveLastElementAdded() throws Exception {
        AutoDiscardingStack<Integer> stack = new AutoDiscardingStack<>(2);

        stack.push(1);
        stack.push(2);

        assertEquals(2, (int) stack.pop());
        assertEquals(1, (int) stack.pop());
        assertEquals(null, stack.pop());
        assertEquals(null, stack.pop());
    }

    @Test
    public void emptyProperty_ShouldHaveTheSameValue() throws Exception {
        AutoDiscardingStack<Integer> stack = new AutoDiscardingStack<>(2);

        assertTrue(stack.emptyProperty().get());
        assertEquals(stack.isEmpty(), stack.emptyProperty().get());
        stack.push(1);
        assertFalse(stack.emptyProperty().get());
        assertEquals(stack.isEmpty(), stack.emptyProperty().get());
        stack.pop();
        assertTrue(stack.emptyProperty().get());
        assertEquals(stack.isEmpty(), stack.emptyProperty().get());
    }
}
