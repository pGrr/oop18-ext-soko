package model;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Interface Position.
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
