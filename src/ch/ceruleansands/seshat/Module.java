/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 CeruleanSands
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

package ch.ceruleansands.seshat;

import ch.ceruleansands.actionstream.ActionHistory;
import ch.ceruleansands.seshat.action.ActionFactory;
import ch.ceruleansands.seshat.gui.GuiFactory;
import ch.ceruleansands.seshat.language.LanguageInitializer;
import ch.ceruleansands.seshat.language.StaticOmniscientInitializer;
import ch.ceruleansands.seshat.language.java.TileFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * @author Thomas Schweizer.
 */
public class Module extends AbstractModule {

    private ActionHistory actionHistory;

    @Override

    protected void configure() {
        install(new FactoryModuleBuilder().build(GuiFactory.class));
        install(new FactoryModuleBuilder().build(ActionFactory.class));
        install(new FactoryModuleBuilder().build(TileFactory.class));

        bind(LanguageInitializer.class).to(StaticOmniscientInitializer.class);
        actionHistory = new ActionHistory(20);
    }


    @Provides
    public ActionHistory provideHistory() {
        return actionHistory;
    }
}
