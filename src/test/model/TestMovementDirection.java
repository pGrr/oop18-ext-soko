package test.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import model.levelsequence.level.grid.MovementDirection;
import model.levelsequence.level.grid.element.PositionImpl;

/**
 * Tests {@link MovementDirection}.
 */
public final class TestMovementDirection {

    /**
     * Tests computeTargetPosition method.
     */
    @Test
    public void testComputeTargetPosition() {
        assertEquals(MovementDirection.UP.computeTargetPosition(new PositionImpl(1, 1)), new PositionImpl(0, 1));
        assertEquals(MovementDirection.DOWN.computeTargetPosition(new PositionImpl(1, 1)), new PositionImpl(2, 1));
        assertEquals(MovementDirection.LEFT.computeTargetPosition(new PositionImpl(1, 1)), new PositionImpl(1, 0));
        assertEquals(MovementDirection.RIGHT.computeTargetPosition(new PositionImpl(1, 1)), new PositionImpl(1, 2));
    }
}
