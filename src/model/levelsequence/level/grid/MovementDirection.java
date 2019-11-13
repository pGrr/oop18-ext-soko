package model.levelsequence.level.grid;

import java.util.function.Function;
import model.levelsequence.level.grid.element.Position;
import model.levelsequence.level.grid.element.PositionImpl;

/**
 * The directions of movement. Each direction is associated with a function that
 * computes the target position given the initial position.
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

    private final transient Function<Position, Position> computeTargetPosition;

    /**
     * Instantiates each direction constant object. Each direction is associated
     * with a function that computes the target position given the initial position.
     *
     * @param computeTargetPosition a function that given the current position
     *                              computes the target position basing upon the
     *                              direction
     */
    MovementDirection(final Function<Position, Position> computeTargetPosition) {
        this.computeTargetPosition = computeTargetPosition;
    }

    /**
     * Computes the target position given an initial position basing on the
     * MovementDirection type it is called on.
     *
     * @param currentPosition the initial position
     * @return the target position
     */
    public final Position computeTargetPosition(final Position currentPosition) {
        return this.computeTargetPosition.apply(currentPosition);
    }
}
