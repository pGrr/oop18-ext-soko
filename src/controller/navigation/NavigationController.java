package controller.navigation;

import model.level.Level;
import view.craft.CraftWindow;
import view.game.GameWindow;
import view.initial.InitialWindow;

/**
 * The navigation controller, which is responsible for showing and hiding
 * Windows.
 */
public interface NavigationController {

    /**
     * Gets the initial window.
     * 
     * @return the initialWindow
     */
    InitialWindow getInitialWindow();

    /**
     * Gets the craft window.
     * 
     * @return the craftWindow
     */
    CraftWindow getCraftWindow();

    /**
     * gets the game window.
     * 
     * @return the gameWindow
     */
    GameWindow getGameWindow();

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
}
