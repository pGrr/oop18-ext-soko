package model;

import java.util.Objects;
import view.View;

// TODO: Auto-generated Javadoc
/**
 * The Class ElementImpl.
 */
public class ElementImpl implements Element {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 477897545325345634L;

    /** The grid. */
    private final Grid grid;

    /** The type. */
    private final Type type;

    /** The position. */
    private Position position;

    /**
     * Instantiates a new element impl.
     *
     * @param type     the type
     * @param position the position
     * @param grid     the grid
     */
    public ElementImpl(Type type, Position position, Grid grid) {
        this.type = type;
        this.position = position;
        this.grid = grid;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public Type getType() {
        return type;
    }

    /**
     * Gets the position.
     *
     * @return the position
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position.
     *
     * @param position the new position
     */
    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Checks if is type movable.
     *
     * @return true, if is type movable
     */
    @Override
    public boolean isTypeMovable() {
        return type.equals(Type.USER) || type.equals(Type.BOX);
    }

    /**
     * Move.
     *
     * @param direction the direction
     */
    @Override
    public void move(Direction direction) {
        if (isTypeMovable()) {
            boolean hasMoved = this.grid.moveAttempt(this, direction);
            if (hasMoved) {
                View.getInstance().getGameWindow().drawElement(this);
            }
        }
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(position, type);
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
        ElementImpl other = (ElementImpl) obj;
        return Objects.equals(position, other.position) && type == other.type;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "ElementImpl [type=" + type + ", position=" + position + ", grid=" + grid + "]";
    }

}
