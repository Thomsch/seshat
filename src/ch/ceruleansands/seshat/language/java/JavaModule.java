package ch.ceruleansands.seshat.language.java;

import ch.ceruleansands.seshat.language.java.action.ActionFactory;
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
