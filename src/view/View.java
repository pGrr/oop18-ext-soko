package view;

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * The Interface View.
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
     * Gets the all windows.
     *
     * @return the all windows
     */
    Collection<Window> getAllWindows();

    /**
     * Gets the window on top.
     *
     * @return the window on top
     */
    Window getWindowOnTop();

    /**
     * Gets the single instance of View.
     *
     * @return single instance of View
     */
    static View getInstance() {
        return ViewSingleton.getInstance();
    }

}