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

package ch.ceruleansands.actionstream;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Thomsch.
 */
public class ActionHistoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void constructor_ShouldThrowExceptionIfSizeIsZero() throws Exception {
        new ActionHistory(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_ShouldThrowExceptionIfSizeIsBelowZero() throws Exception {
        new ActionHistory(-1);
    }

    @Test
    public void forward_ShouldThrowNPEIfNullAction() throws Exception {
        ActionHistory actionHistory = new ActionHistory(1);

        try {
            actionHistory.forward(null);
        } catch (NullPointerException e) {
            return;
        }
        fail();
    }

    @Test
    public void forward_ShouldExecuteTheActionAndStoreIt() throws Exception {
        ActionHistory actionHistory = new ActionHistory(1);
        TestAction action = new TestAction();

        actionHistory.forward(action);

        assertEquals(1, action.getNbrExecuted());
        actionHistory.undo();
        assertEquals(1, action.getNbrReverted());
    }

    @Test
    public void add_ShouldStoreIt() throws Exception {
        ActionHistory actionHistory = new ActionHistory(1);
        TestAction action = new TestAction();

        actionHistory.forward(action);

        assertEquals(0, action.getNbrExecuted());
        actionHistory.undo();
        assertEquals(1, action.getNbrReverted());
    }

    @Test
    public void undo_ShouldRevertTheAction() throws Exception {
        ActionHistory actionHistory = new ActionHistory(2);
        TestAction action = new TestAction();
        TestAction action2 = new TestAction();

        actionHistory.add(action);
        actionHistory.add(action2);
        actionHistory.undo();
        actionHistory.undo();
        assertEquals(1, action.getNbrReverted());
        assertEquals(1, action2.getNbrReverted());
    }

    @Test
    public void undo_ShouldDoNothingIfEmpty() throws Exception {
        ActionHistory actionHistory = new ActionHistory(2);

        actionHistory.undo();
        actionHistory.undo();
    }

    @Test
    public void redo_ShouldReExecuteActionWhenUnDone() throws Exception {
        ActionHistory actionHistory = new ActionHistory(3);

        TestAction action1 = new TestAction();
        TestAction action2 = new TestAction();
        TestAction action3 = new TestAction();

        actionHistory.add(action1);
        actionHistory.add(action2);
        actionHistory.add(action3);
        actionHistory.undo();
        actionHistory.redo();

        assertEquals(0, action1.getNbrExecuted());
        assertEquals(0, action1.getNbrReverted());
        assertEquals(0, action2.getNbrExecuted());
        assertEquals(0, action2.getNbrReverted());
        assertEquals(1, action3.getNbrExecuted());
        assertEquals(1, action3.getNbrReverted());
    }

    private class TestAction implements Action {

        private int nbrExecuted = 0;
        private int nbrReverted = 0;

        @Override
        public void execute() {
            nbrExecuted++;
        }

        @Override
        public void revert() {
            nbrReverted++;
        }

        /**
         * Returns the number of times this action was executed.
         * @return the number of times this actions has been executed
         */
        public int getNbrExecuted() {
            return nbrExecuted;
        }

        /**
         * Returns the number of times this action was executed.
         * @return the number of times this actions has been executed
         */
        public int getNbrReverted() {
            return nbrReverted;
        }
    }
}
