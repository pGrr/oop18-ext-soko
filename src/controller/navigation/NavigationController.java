package controller.navigation;

import model.Level;

/**
 * The navigation controller, which is responsible for showing and hiding
 * Windows.
 */
public interface NavigationController {

    /**
     * Shows the initial view, which contains the level sequence editor and the main
     * navigation options (craft, play and save/load sequence).
     */
    void toInitialView();

    /**
     * Shows the craft level view.
     */
    void toCraftLevelView();

    /**
     * Shows the game view of a given level.
     *
     * @param level the level to be played
     */
    void toGameLevelView(Level level);

    /**
     * Gets the default navigation controller object.
     *
     * @return the single instance of the default implementing class, which is
     *         {@link NavigationControllerSingleton}
     */
    static NavigationController getDefaultInstance() {
        return NavigationControllerSingleton.getInstance();
    }
}
