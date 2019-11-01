package view;

import java.util.Collection;

import view.craft.CraftWindow;
import view.game.GameWindow;
import view.initial.InitialWindow;

/**
 * The main view interface, it is the entry point to get the references to any
 * view interface.
 */
public interface View {

    /**
     * Gets the initial window.
     *
     * @return the initial window
     */
    InitialWindow getInitialWindow();

    /**
     * Gets the craft window.
     *
     * @return the craft window
     */
    CraftWindow getCraftWindow();

    /**
     * Gets the game window.
     *
     * @return the game window
     */
    GameWindow getGameWindow();

    /**
     * Gets all windows.
     *
     * @return a collection of all windows
     */
    Collection<Window> getAllWindows();

    /**
     * Gets the single instance of View.
     *
     * @return single instance of View
     */
    static View getInstance() {
        return ViewSingleton.getInstance();
    }
}
