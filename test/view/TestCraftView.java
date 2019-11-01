package view;

import view.craft.CraftWindow;
import view.craft.CraftWindowImpl;

/**
 * The Class TestCraftView.
 */
public final class TestCraftView {

    /**
     * Instantiates a new test craft view.
     */
    private TestCraftView() {
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String... args) {
        CraftWindow craftView = new CraftWindowImpl();
        craftView.show();
    }

}
