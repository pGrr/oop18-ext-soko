package model;

import java.util.Objects;
import view.View;

/**
 * An implementation class for the {@link Element} interface.
 */
public class ElementImpl implements Element {

    private static final long serialVersionUID = 477897545325345634L;

    private final Grid grid;
    private final Type type;
    private Position position;

    /**
     * Instantiates a new ElementImpl object with a given type and position and
     * specifying the grid to which the element belongs.
     *
     * @param type     the type of the element
     * @param position the position of the element
     * @param grid     the grid to which the element belongs
     */
    public ElementImpl(final Type type, final Position position, final Grid grid) {
        this.type = type;
        this.position = position;
        this.grid = grid;
    }

    @Override
    public final Type getType() {
        return type;
    }

    @Override
    public final Position getPosition() {
        return position;
    }

    @Override
    public final void setPosition(final Position position) {
        this.position = position;
    }

    @Override
    public final boolean isTypeMovable() {
        return type.equals(Type.USER) || type.equals(Type.BOX);
    }

    @Override
    public final void move(final Direction direction) {
        if (isTypeMovable()) {
            boolean hasMoved = this.grid.moveAttempt(this, direction);
            if (hasMoved) {
                View.getInstance().getGameWindow().draw(this);
            }
        }
    }

    @Override
    public final int hashCode() {
        return Objects.hash(position, type);
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
        ElementImpl other = (ElementImpl) obj;
        return Objects.equals(position, other.position) && type == other.type;
    }

    @Override
    public final String toString() {
        return "ElementImpl [type=" + type + ", position=" + position + ", grid=" + grid + "]";
    }
}
