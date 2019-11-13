package controller.initial;

import java.io.IOException;
import model.levelsequence.level.LevelNotValidException;

/**
 * The {@link view.initial.InitialWindow} controller.
 */
public interface InitialWindowController {

    /**
     * Tells the controller to load the level at the given path.
     *
     * @param path the absolute path in the file-system from which to load the level
     *             to be added
     * @throws ClassNotFoundException if the loaded object is not recognized (e.g.
     *                                level is corrupted)
     * @throws LevelNotValidException if the level is not valid
     * @throws IOException            if an I/O exception has occurred.
     */
    void addLevel(String path) throws ClassNotFoundException, LevelNotValidException, IOException;

    /**
     * Tells the controller to swap the elements of the given indexes in the current
     * level sequence.
     * 
     * @param i one index to be swapped
     * @param j the other index to be swapped
     */
    void swap(int i, int j);

    /**
     * Tells the controller to remove the level with the given index from the
     * current level sequence.
     *
     * @param i the index of the element to be removed
     */
    void remove(int i);

    /**
     * Tells the controller to remove all of the elements of the current level
     * sequence.
     */
    void removeAll();

    /**
     * Tells the controller to go back to the craft level view.
     */
    void toCraftLevelView();

    /**
     * Tells the controller to start the playing of the current level sequence.
     */
    void play();

    /**
     * Tells the controller to sync the level list with the model level sequence.
     */
    void updateLevelList();

    /**
     * Saves the current level sequence with the given name to the given path in the
     * file-system.
     * 
     * @param name the name of the level sequence
     * @param path the absolute path to which save the level sequence
     * @throws IOException if an input/output exception occurs
     */
    void saveLevelSequence(String name, String path) throws IOException;

    /**
     * Loads the level sequence from the file-system at the given path.
     * 
     * @param path the path from which to load the level sequence in the file-system
     * @throws ClassNotFoundException if the loaded object is not recognized (e.g. a
     *                                level is corrupted)
     * @throws IOException            if an input/output exception occurs
     */
    void loadLevelSequence(String path) throws ClassNotFoundException, IOException;
}
