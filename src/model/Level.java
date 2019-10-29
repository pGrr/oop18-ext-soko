package model;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Interface Level.
 */
public interface Level extends Serializable {

    /**
     * Gets the name.
     *
     * @return the name
     */
    String getName();

    /**
     * Gets the grid.
     *
     * @return the grid
     */
    Grid getGrid();

    /**
     * Validate.
     *
     * @throws LevelNotValidException the level not valid exception
     */
    void validate() throws LevelNotValidException;

    /**
     * Gets the user.
     *
     * @return the user
     */
    Element getUser();

    /**
     * Checks if is finished.
     *
     * @return true, if is finished
     */
    boolean isFinished();

    /**
     * Equals.
     *
     * @param obj the obj
     * @return true, if successful
     */
    @Override
    public boolean equals(Object obj);

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode();
}
