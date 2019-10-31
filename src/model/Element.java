package model;

import java.io.Serializable;

/**
 * An element of the game, which has a type and a position.
 */
public interface Element extends Serializable {

    /**
     * Gets the type of the element, which stays constant.
     *
     * @return the type of the element
     */
    Type getType();

    /**
     * Gets the position of the element, which can change in time.
     *
     * @return the current position of the element
     */
    Position getPosition();

    /**
     * Sets the position of the element.
     *
     * @param position the new position of the element
     */
    void setPosition(Position position);

    /**
     * Checks if the element is movable (e.g. user or box).
     *
     * @return true, if is type movable
     */
    boolean isTypeMovable();

    /**
     * Moves the element in a given direction if that movement is possible.
     *
     * @param direction the direction
     */
    void move(Direction direction);

    /**
     * The element hash code is computed on its position and type.
     *
     * @return the computed hashCode
     */
    @Override
    int hashCode();

    /**
     * Elements are compared basing upon their position and type.
     *
     * @param obj the object to be compared with the element
     * @return true, if successful
     */
    @Override
    boolean equals(Object obj);
}
