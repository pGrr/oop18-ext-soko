package model;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Interface Element.
 */
public interface Element extends Serializable {

    /**
     * Gets the type.
     *
     * @return the type
     */
    Type getType();

    /**
     * Gets the position.
     *
     * @return the position
     */
    Position getPosition();

    /**
     * Sets the position.
     *
     * @param position the new position
     */
    void setPosition(Position position);

    /**
     * Checks if is type movable.
     *
     * @return true, if is type movable
     */
    boolean isTypeMovable();

    /**
     * Move.
     *
     * @param direction the direction
     */
    void move(Direction direction);

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
