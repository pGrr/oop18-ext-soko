package controller;

import controller.initial.InitialWindowController;
import controller.craft.CraftWindowController;
import controller.game.GameWindowController;
import controller.navigation.NavigationController;
import controller.sequence.LevelSequenceController;

/**
 * The main controller interface, it is the entry point to get any controller
 * function. It is responsible to start the application and to get a reference
 * to more specific controllers.
 */
public interface Controller {

    /**
     * Gets the navigation controller, which is responsible for showing and hiding
     * Windows.
     *
     * @return the navigation controller
     */
    NavigationController getNavigationController();

    /**
     * Gets the sequence controller, which is responsible for the main operations
     * concerning level sequences.
     *
     * @return the sequence controller
     */
    LevelSequenceController getLevelSequenceController();

    /**
     * Gets the initial window controller.
     *
     * @return the initial window controller
     */
    InitialWindowController getInitialWindowObserver();

    /**
     * Gets the craft window controller.
     *
     * @return the game controller
     */
    CraftWindowController getCraftWindowController();

    /**
     * Gets the game window controller.
     *
     * @return the game window controller
     */
    GameWindowController getGameWindowController();

    /**
     * Gets the single instance of Controller.
     *
     * @return single instance of Controller
     */
    static Controller getInstance() {
        return ControllerSingleton.getInstance();
    }
}
