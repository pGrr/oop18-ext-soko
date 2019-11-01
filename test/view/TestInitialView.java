package view;

import view.initial.InitialWindow;
import view.initial.InitialWindowImpl;

/**
 * The Class TestInitialView.
 */
public final class TestInitialView {

    /**
     * Instantiates a new test initial view.
     */
    private TestInitialView() {
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String... args) {
        InitialWindow initialView = new InitialWindowImpl();
        initialView.show();
    }
}
