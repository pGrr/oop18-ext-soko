package model.levelsequence.level.grid.element;

import java.io.Serializable;

/**
 * A grid position, which has a row index and a column index.
 */
public interface Position extends Serializable {

    /**
     * Gets the row index.
     *
     * @return the row index
     */
    int getRowIndex();

    /**
     * Gets the column index.
     *
     * @return the column index
     */
    int getColumnIndex();

    /**
     * Two positions are equal if their row index and column index are equals.
     *
     * @param obj the object to be compared
     * @return true, if successful
     */
    @Override 
    boolean equals(Object obj);

    /**
     * The hash code of a position is computed on its row and column indexes.
     *
     * @return the computed hash code
     */
    @Override 
    int hashCode();
}
