package ch.thomsch.seshat.guice;

import ch.thomsch.actionhistory.ActionHistory;
import ch.thomsch.seshat.action.ActionFactory;
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
        install(new FactoryModuleBuilder().build(ActionFactory.class));

        actionHistory = new ActionHistory(20);
    }


    @Provides
    public ActionHistory provideHistory() {
        return actionHistory;
    }
}