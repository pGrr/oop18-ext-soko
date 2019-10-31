package model.level;

import java.io.Serializable;

import model.element.Element;
import model.grid.Grid;

/**
 * A Level of the game. Has a name and a grid, it can be validated and played.
 */
public interface Level extends Serializable {

    /**
     * Gets the name of the level.
     *
     * @return the name of the level
     */
    String getName();

    /**
     * Gets the grid of the level.
     *
     * @return the grid of the level
     */
    Grid getGrid();

    /**
     * Gets the user element.
     *
     * @return the user element
     */
    Element getUser();

    /**
     * Checks if the level is finished, i.e. all the boxes are on a target.
     *
     * @return true, if is finished
     */
    boolean isFinished();

    /**
     * Validates the level. It checks that the level has exactly one user, at least
     * one target and an equal number of targets and boxes. If not correct, it
     * throws a {@link LevelNotValidException}.
     *
     * @throws LevelNotValidException an application-specific exception meaning the
     *                                level is not correct
     */
    void validate() throws LevelNotValidException;

    /**
     * The hash code of a level is computed on its name and grid.
     *
     * @return the computed hashcode
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
