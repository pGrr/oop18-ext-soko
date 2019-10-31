package model.position;

import java.util.Objects;

/**
 * An implementation of the {@link Position} interface.
 */
public class PositionImpl implements Position {

    private static final long serialVersionUID = 8528254874754254096L;

    private final int rowIndex;
    private final int columnIndex;

    /**
     * Instantiates a new position with the given row and column indexes.
     *
     * @param rowIndex    the row index
     * @param columnIndex the column index
     */
    public PositionImpl(final int rowIndex, final int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    @Override
    public final int getRowIndex() {
        return rowIndex;
    }

    @Override
    public final int getColumnIndex() {
        return columnIndex;
    }

    @Override
    public final String toString() {
        return "PositionImpl [rowIndex=" + rowIndex + ", columnIndex=" + columnIndex + "]";
    }

    @Override
    public final int hashCode() {
        return Objects.hash(columnIndex, rowIndex);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PositionImpl other = (PositionImpl) obj;
        return columnIndex == other.columnIndex && rowIndex == other.rowIndex;
    }
}
