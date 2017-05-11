package ch.ceruleansands.seshat.guice;

import ch.ceruleansands.seshat.action.ActionFactory;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * @author Thomsch
 */
public class JavaModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().build(ActionFactory.class));
    }
}
