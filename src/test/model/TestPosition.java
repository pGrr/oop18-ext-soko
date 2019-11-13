package test.model;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import model.levelsequence.level.grid.element.Position;
import model.levelsequence.level.grid.element.PositionImpl;

/**
 * Tests {@link Position} interface.
 */
public final class TestPosition {

    /**
     * Tests all Position methods.
     */
    @Test
    public void testPosition() {
        Position p0 = new PositionImpl(0, 1);
        Position p1 = new PositionImpl(1, 2);
        Position p2 = new PositionImpl(2, 3);
        // get row index
        assertEquals(0, p0.getRowIndex());
        assertEquals(1, p1.getRowIndex());
        assertEquals(2, p2.getRowIndex());
        // get column index
        assertEquals(1, p0.getColumnIndex());
        assertEquals(2, p1.getColumnIndex());
        assertEquals(3, p2.getColumnIndex());
        // equals
        assertEquals(p0, new PositionImpl(0, 1));
        assertEquals(p1, new PositionImpl(1, 2));
        assertEquals(p2, new PositionImpl(2, 3));
        assertNotEquals(p0, new PositionImpl(0, 0));
        assertNotEquals(p0, new PositionImpl(1, 1));
    }
}
