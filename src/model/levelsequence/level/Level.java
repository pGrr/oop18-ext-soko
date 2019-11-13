package model.levelsequence.level;

import java.io.Serializable;
import model.levelsequence.level.grid.Grid;
import model.levelsequence.level.grid.element.Element;

/**
 * A Level of the game, which has a name and a validated {@link Grid}.
 */
public interface Level extends Serializable {

    /**
     * Gets the name.
     *
     * @return the name
     */
    String getName();

    /**
     * Gets the grid in its initial state.
     * 
     * @return the grid in its initial state
     */
    Grid getInitialGrid();

    /**
     * Gets the grid in its current state.
     *
     * @return the grid in its current state
     */
    Grid getCurrentGrid();

    /**
     * Gets the user {@link Element}.
     *
     * @return the user
     */
    Element getUser();

    /**
     * Checks if the level is finished, i.e. all the boxes are on a target.
     *
     * @return true, if finished
     */
    boolean isFinished();

    /**
     * Validates the level. It checks that the level has exactly one user, at least
     * one target and an equal number of targets and boxes. If not correct, it
     * throws a {@link LevelNotValidException}.
     *
     * @throws LevelNotValidException if level is not correct
     */
    void validate() throws LevelNotValidException;

    /**
     * The hash code of a level is computed on its name and grid.
     *
     * @return the computed hash code
     */
    @Override
    int hashCode();

    /**
     * Two levels are equals if they have equal name and equal grid.
     *
     * @param obj the object to be compared
     * @return true, if successful
     */
    @Override
    boolean equals(Object obj);
}
