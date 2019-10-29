package model;

import java.io.Serializable;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface LevelSequence.
 */
public interface LevelSequence extends Serializable {

    /**
     * Gets the name.
     *
     * @return the name
     */
    String getName();

    /**
     * Gets the all levels.
     *
     * @return the all levels
     */
    List<Level> getAllLevels();

    /**
     * Adds the.
     *
     * @param level the level
     */
    void add(Level level);

    /**
     * Swap.
     *
     * @param i the i
     * @param j the j
     */
    void swap(int i, int j);

    /**
     * Removes the.
     *
     * @param i the i
     */
    void remove(int i);

    /**
     * Clear.
     */
    void clear();

    /**
     * Checks for next level.
     *
     * @return true, if successful
     */
    boolean hasNextLevel();

    /**
     * Sets the next level.
     */
    void setNextLevel();

    /**
     * Checks for current level.
     *
     * @return true, if successful
     */
    boolean hasCurrentLevel();

    /**
     * Gets the current level.
     *
     * @return the current level
     */
    Level getCurrentLevel();

    /**
     * Default create empty.
     *
     * @param name the name
     * @return the level sequence
     */
    static LevelSequence defaultCreateEmpty(String name) {
        return new LevelSequenceImpl(name);
    }

    /**
     * Creates the from levels.
     *
     * @param name   the name
     * @param levels the levels
     * @return the level sequence
     */
    static LevelSequence createFromLevels(String name, List<Level> levels) {
        return new LevelSequenceImpl(name, levels);
    }
}
