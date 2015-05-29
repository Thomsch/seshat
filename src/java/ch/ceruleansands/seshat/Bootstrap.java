package ch.ceruleansands.seshat;

import ch.ceruleansands.seshat.gui.Module;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Thomas Schweizer.
 */
public class Bootstrap {

    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new Module());

        Seshat seshat = injector.getInstance(Seshat.class);
        seshat.start();
    }
}
