package app;

import controller.Controller;

/**
 * The Class containing the main method to start the Sokoban application.
 */
public final class SokobanApp {

    private SokobanApp() {
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {
        Controller.getInstance().startApplication();
    }

}
