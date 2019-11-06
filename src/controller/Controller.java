package controller;

import controller.initial.InitialWindowController;
import controller.craft.CraftWindowController;
import controller.game.GameWindowController;

/**
 * The main controller interface, it is the entry point to get any controller
 * function. It is responsible to start the application and to get a reference
 * to more specific controllers.
 */
public interface Controller {

    /**
     * Gets the initial window controller.
     *
     * @return the initial window controller
     * @throws IllegalStateException if the model and view have not been set for this
     *                               controller prior to this call
     */
    InitialWindowController getInitialWindowController() throws IllegalStateException;

    /**
     * Gets the craft window controller.
     *
     * @return the game controller
     * @throws IllegalStateException if the model and view have not been set for this
     *                               controller prior to this call
     */
    CraftWindowController getCraftWindowController() throws IllegalStateException;

    /**
     * Gets the game window controller.
     *
     * @return the game window controller
     * @throws IllegalStateException if the model and view have not been set for this
     *                               controller prior to this call
     */
    GameWindowController getGameWindowController() throws IllegalStateException;
}
