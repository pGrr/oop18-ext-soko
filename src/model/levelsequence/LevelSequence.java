package model.levelsequence;

import java.io.Serializable;
import java.util.List;

import model.levelsequence.level.Level;

/**
 * An editable, ordered and iterable sequence of levels, with a name.
 */
public interface LevelSequence extends Serializable {

    /**
     * Gets the name of the level sequence.
     *
     * @return the name of the level sequence
     */
    String getName();

    /**
     * Gets the current ordered sequence of levels.
     *
     * @return the current ordered sequence of levels as list
     */
    List<Level> getAllLevels();

    /**
     * Adds the given level.
     *
     * @param level the level to be added to the sequence
     */
    void add(Level level);

    /**
     * Swap the levels with the specified indexes in the sequence.
     *
     * @param i an element index
     * @param j another element index
     */
    void swap(int i, int j);

    /**
     * Removes the level with the given index from the sequence.
     *
     * @param i the index of the level to be removed
     */
    void remove(int i);

    /**
     * Clears the list removing all the levels.
     */
    void clear();

    /**
     * Checks if the current iteration has a next level.
     *
     * @return true, if there is a next level
     */
    boolean hasNextLevel();

    /**
     * Sets the next level as the current level.
     */
    void setNextLevelAsCurrent();

    /**
     * Gets the current level in its current state.
     *
     * @return the current level
     */
    Level getCurrentLevel();

    /**
     * Restarts the current level bringing back its initial state.
     */
    void restartCurrentLevel();

    /**
     * Creates the copy of the level sequence object.
     *
     * @return the level sequence copy
     */
    LevelSequence createCopy();
}
