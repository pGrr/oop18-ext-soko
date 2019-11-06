package controller.initial;

import java.io.IOException;
import model.levelsequence.level.LevelNotValidException;

/**
 * The initial window controller which controls the behavior subsequently to
 * user interaction in the initial window.
 */
public interface InitialWindowController {

    /**
     * This is the action listener for the "add a level" button. It adds a level to
     * the model and then syncs the list.
     *
     * @param path the absolute from which to load the level to be added
     * @throws ClassNotFoundException the class not found exception
     * @throws LevelNotValidException the level not valid exception
     * @throws IOException            Signals that an I/O exception has occurred.
     */
    void addLevel(String path) throws ClassNotFoundException, LevelNotValidException, IOException;

    /**
     * This is the action listener for the "move level up" and "move level down"
     * buttons. It changes the order of the elements accordingly in the model and
     * then it syncs the list.
     * 
     * @param i one of the indexes to be swapped
     * @param j one of the indexes to be swapped
     */
    void swap(int i, int j);

    /**
     * This is the action listener for the "remove level" button. It removes the
     * level from the sequence in the model and then syncs the list.
     *
     * @param i the index of the element to be removed
     */
    void remove(int i);

    /**
     * This is the action listener for the "reset list" button. It clears the
     * sequence in the model and then syncs the list.
     */
    void removeAll();

    /**
     * Shows craft level view.
     */
    void toCraftLevelView();

    /**
     * Starts the playing of the current level sequence.
     */
    void play();

    /**
     * Saves the current level sequence with the given name to the given path in the
     * file-system.
     * 
     * @param name the name of the level sequence
     * @param path the absolute path to which save the level sequence
     * @throws IOException an input output exception
     */
    void saveLevelSequence(String name, String path) throws IOException;

    /**
     * Loads the level sequence from the file-system at the given path.
     * 
     * @param path the path from which to load the level sequence in the file-system
     * @throws ClassNotFoundException a class not found exception
     * @throws IOException            an input output exception
     */
    void loadLevelSequence(String path) throws ClassNotFoundException, IOException;

    /**
     * Syncs the initial window level list with the model current data.
     */
    void updateLevelList();
}
