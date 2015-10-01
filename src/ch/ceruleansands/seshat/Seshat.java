package ch.ceruleansands.seshat;

import ch.ceruleansands.seshat.gui.Gui;
import com.google.inject.Inject;

/**
 * @author Thomas Schweizer.
 */
public class Seshat {

    private Gui gui;

    @Inject
    public Seshat(Gui gui) {
        this.gui = gui;
    }

    public void start() {
        gui.start();
    }
}
