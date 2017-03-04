package ch.ceruleansands.seshat.guice;

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
