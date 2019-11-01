package model.grid;

import java.util.function.Function;
import model.element.Position;
import model.element.PositionImpl;

/**
 * The directions of movement.
 */
public enum MovementDirection {

    /**
     * The upwards movement direction.
     */
    UP(position -> new PositionImpl(position.getRowIndex() - 1, position.getColumnIndex())),

    /**
     * The downwards movement direction.
     */
    DOWN(position -> new PositionImpl(position.getRowIndex() + 1, position.getColumnIndex())),

    /**
     * The leftwards movement direction.
     */
    LEFT(position -> new PositionImpl(position.getRowIndex(), position.getColumnIndex() - 1)),

    /**
     * The rightwards movement direction.
     */
    RIGHT(position -> new PositionImpl(position.getRowIndex(), position.getColumnIndex() + 1));

    /** The compute target position. */
    private final Function<Position, Position> computeTargetPosition;

    /**
     * Instantiates a new direction constant object.
     *
     * @param computeTargetPosition a function that given the current position
     *                              computes the target position basing upon the
     *                              direction
     */
    MovementDirection(final Function<Position, Position> computeTargetPosition) {
        this.computeTargetPosition = computeTargetPosition;
    }

    /**
     * Given the current position, it computes the target position basing on this
     * direction.
     *
     * @param currentPosition the current position
     * @return the target position
     */
    public final Position computeTargetPosition(final Position currentPosition) {
        return this.computeTargetPosition.apply(currentPosition);
    }
}
