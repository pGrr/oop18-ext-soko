package model.position;

import java.util.Objects;

// TODO: Auto-generated Javadoc
/**
 * The Class PositionImpl.
 */
public class PositionImpl implements Position {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8528254874754254096L;

    /** The row index. */
    private final int rowIndex;

    /** The column index. */
    private final int columnIndex;

    /**
     * Instantiates a new position impl.
     *
     * @param rowIndex    the row index
     * @param columnIndex the column index
     */
    public PositionImpl(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    /**
     * Gets the row index.
     *
     * @return the row index
     */
    @Override
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * Gets the column index.
     *
     * @return the column index
     */
    @Override
    public int getColumnIndex() {
        return columnIndex;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "PositionImpl [rowIndex=" + rowIndex + ", columnIndex=" + columnIndex + "]";
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(columnIndex, rowIndex);
    }

    /**
     * Equals.
     *
     * @param obj the obj
     * @return true, if successful
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PositionImpl other = (PositionImpl) obj;
        return columnIndex == other.columnIndex && rowIndex == other.rowIndex;
    }
}
