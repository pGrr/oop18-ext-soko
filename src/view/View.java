package view;

import controller.Controller;
import model.levelsequence.level.Level;
import view.craft.CraftWindow;
import view.game.GameWindow;
import view.initial.InitialWindow;

/**
 * The View interface, which is responsible for Windows.
 */
public interface View {

    /**
     * Sets the controller.
     *
     * @param controller the controller
     * @throws IllegalStateException if the controller was not set using
     *                               {@link #setController(Controller)} prior to
     *                               this call
     */
    void setController(Controller controller);

    /**
     * Gets the initial window.
     * 
     * @return the initialWindow
     * @throws IllegalStateException if the controller was not set using
     *                               {@link #setController(Controller)} prior to
     *                               this call
     */
    InitialWindow getInitialWindow() throws IllegalStateException;

    /**
     * Gets the craft window.
     * 
     * @return the craftWindow
     * @throws IllegalStateException if the controller was not set using
     *                               {@link #setController(Controller)} prior to
     *                               this call
     */
    CraftWindow getCraftWindow() throws IllegalStateException;

    /**
     * Gets the game window.
     * 
     * @return the gameWindow
     * @throws IllegalStateException if the controller was not set using
     *                               {@link #setController(Controller)} prior to
     *                               this call
     */
    GameWindow getGameWindow() throws IllegalStateException;

    /**
     * Shows the initial view.
     * 
     * @throws IllegalStateException if the controller was not set using
     *                               {@link #setController(Controller)} prior to
     *                               this call
     */
    void toInitialView() throws IllegalStateException;

    /**
     * Shows the craft level view.
     * 
     * @throws IllegalStateException if the controller was not set using
     *                               {@link #setController(Controller)} prior to
     *                               this call
     */
    void toCraftLevelView() throws IllegalStateException;

    /**
     * Shows the game view of a given level.
     *
     * @param level the level to be played
     * @throws IllegalStateException if the controller was not set using
     *                               {@link #setController(Controller)} prior to
     *                               this call
     */
    void toGameLevelView(Level level) throws IllegalStateException;
}
