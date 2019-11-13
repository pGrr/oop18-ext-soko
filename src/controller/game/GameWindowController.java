package controller.game;

import java.io.IOException;
import model.levelsequence.level.Level;
import model.levelsequence.level.grid.MovementDirection;

/**
 * The {@link view.game.GameWindow} controller.
 */
public interface GameWindowController {

    /**
     * It is called when the player triggers the movement event and it updates the
     * model subsequently basing upon the game logics (the movement can result in a
     * change or not).
     *
     * @param direction the direction of the movement
     */
    void move(MovementDirection direction);

    /**
     * Retrieves the current level from the model.
     * 
     * @return the current level
     */
    Level getCurrentLevel();

    /**
     * Restarts the current level. Every movement performed since the beginning of
     * the level will be lost and every element will return to the original
     * position.
     */
    void restartCurrentLevel();

    /**
     * Starts the next level. This is only called when a level is finished and there
     * is another one next (after user accepted the corresponding message).
     */
    void levelFinishedAccepted();

    /**
     * Goes back to the initial view. This is only called when the last level of a
     * sequence is finished (after user accepted the corresponding message).
     */
    void gameFinishedAccepted();

    /**
     * Saves the current game. More specifically, it creates a new level sequence
     * with the current level in its current state as a first level and all the
     * ordered remaining levels next to it.
     *
     * @param path the absolute path of the file to be saved
     * @throws IOException if an I/O exception occurred.
     */
    void saveGame(String path) throws IOException;

    /**
     * Goes back to initial view.
     */
    void backToInitialView();
}
