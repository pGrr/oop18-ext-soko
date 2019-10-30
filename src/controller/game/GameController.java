package controller.game;

import java.io.IOException;

import model.Direction;

/**
 * The Game controller, which is responsible for the operations necessary when
 * playing a level.
 */
public interface GameController {

    /**
     * Performs the action of the user trying to move in a specified direction. It
     * is called from the view when the player triggers the movement event and it
     * updates the model subsequently upon the game logics (the movement can result
     * in a change of element positions or not).
     *
     * @param direction the direction of the movement
     */
    void move(Direction direction);

    /**
     * Restarts the current level. Every movement performed since the beginning of
     * the level will be lost and every element will return to the original
     * position. This is called from the view when the user triggers the
     * corresponding event.
     */
    void restartCurrentLevel();

    /**
     * This is called when the user accepts the level finished message which is
     * called when a level is finished and there is another one next. This function
     * starts the next level.
     */
    void levelFinishedAccepted();

    /**
     * This is called when the user accepts the game finished message, wich is
     * called when the last level of a sequence is finished. This function go back
     * to the initial view.
     */
    void gameFinishedAccepted();

    /**
     * Saves the current game. More specifically, it creates a new level sequence
     * with the current level in its current state as a first level and all the
     * ordered remaining levels next to it.
     *
     * @param path the path of the file to be saved
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void saveGame(String path) throws IOException;

    /**
     * Gets the default game controller object.
     *
     * @return the single instance of the default implementing class, which is
     *         {@link GameControllerSingleton}.
     */
    static GameController getDefaultInstance() {
        return GameControllerSingleton.getInstance();
    }
}
