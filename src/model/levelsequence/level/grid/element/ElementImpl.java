package model.levelsequence.level.grid.element;

import java.util.Objects;
import model.levelsequence.level.grid.Grid;
import model.levelsequence.level.grid.MovementDirection;

/**
 * An implementation of {@link Element}.
 */
public final class ElementImpl implements Element {

    private static final long serialVersionUID = 477897545325345634L;

    private final Grid grid;
    private final Type type;
    private Position position;

    /**
     * Creates a new element using the given type and position, which belongs to the given grid.
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
    public Type getType() {
        return this.type;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(final Position position) {
        this.position = position;
    }

    @Override
    public boolean isTypeMovable() {
        return this.type.equals(Type.USER) || this.type.equals(Type.BOX);
    }

    @Override
    public void move(final MovementDirection direction) {
        if (isTypeMovable()) {
            this.grid.moveAttempt(this, direction);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.position, this.type);
    }

    @Override
    public boolean equals(final Object obj) {
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
        return Objects.equals(this.position, other.position) && this.type == other.type;
    }

    @Override
    public String toString() {
        return "ElementImpl [type=" + this.type + ", position=" + this.position + ", grid=" + this.grid + "]";
    }
}
