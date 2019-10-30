package controller;

import controller.game.GameController;
import controller.level.LevelController;
import controller.navigation.NavigationController;
import controller.sequence.LevelSequenceController;

/**
 * The main controller interface, it is the entry point for the View and the
 * model. It is responsible to start the application and to get a reference to
 * more specific controllers.
 */
public interface Controller {

    /**
     * Starts the application.
     */
    void startApplication();

    /**
     * Gets the navigation controller, which is responsible for showing and hiding
     * Windows.
     *
     * @return the navigation controller
     */
    NavigationController getNavigationController();

    /**
     * Gets the level controller, which is responsible for the main operations
     * concerning levels.
     *
     * @return the level controller
     */
    LevelController getLevelController();

    /**
     * Gets the sequence controller, which is responsible for the main operations
     * concerning level sequences.
     *
     * @return the sequence controller
     */
    LevelSequenceController getLevelSequenceController();

    /**
     * Gets the game controller, which is responsible for the operations necessary
     * when playing a level.
     *
     * @return the game controller
     */
    GameController getGameController();

    /**
     * Gets the single instance of Controller.
     *
     * @return single instance of Controller
     */
    static Controller getInstance() {
        return ControllerSingleton.getInstance();
    }
}
