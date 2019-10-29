package controller;

import model.Level;

// TODO: Auto-generated Javadoc
/**
 * The Interface NavigationController.
 */
public interface NavigationController {

    /**
     * To craft level.
     */
    void toCraftLevel();

    /**
     * To game level.
     *
     * @param levelSchema the level schema
     */
    void toGameLevel(Level levelSchema);

    /**
     * To initial view.
     */
    void toInitialView();

    /**
     * Gets the default instance.
     *
     * @return the default instance
     */
    static NavigationController getDefaultInstance() {
        return NavigationControllerImpl.getInstance();
    }

}
