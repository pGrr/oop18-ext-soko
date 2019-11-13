package model.levelsequence.level.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import model.levelsequence.level.grid.element.Element;
import model.levelsequence.level.grid.element.ElementImpl;
import model.levelsequence.level.grid.element.Position;
import model.levelsequence.level.grid.element.PositionImpl;
import model.levelsequence.level.grid.element.Type;

/**
 * An implementation for {@link Grid}.
 */
public class GridImpl implements Grid {

    private static final long serialVersionUID = -1944841853255090464L;
    private final List<Element> elements;

    /**
     * Creates an empty instance.
     */
    public GridImpl() {
        super();
        this.elements = new ArrayList<>();
    }

    /**
     * Creates a new grid which is the copy of the given grid.
     * 
     * @param grid the grid
     */
    public GridImpl(final Grid grid) {
        this.elements = new ArrayList<>();
        grid.getAllElements().forEach(e -> this.elements.add(new ElementImpl(e.getType(),
                new PositionImpl(e.getPosition().getRowIndex(), e.getPosition().getColumnIndex()), this)));
    }

    @Override
    public final void add(final Element element) {
        this.elements.add(element);
    }

    @Override
    public final void remove(final Element element) {
        this.elements.remove(element);
    }

    @Override
    public final void clear() {
        this.elements.clear();
    }

    @Override
    public final List<Element> getAllElements() {
        return this.elements;
    }

    @Override
    public final List<Element> getBoxesOnTarget() {
        List<Element> boxes = this.elements.stream().filter(e -> e.getType().equals(Type.BOX))
                .collect(Collectors.toList());
        List<Element> targets = this.elements.stream().filter(e -> e.getType().equals(Type.TARGET))
                .collect(Collectors.toList());
        List<Element> boxesOnTarget = new ArrayList<>();
        boxes.forEach(b -> {
            targets.forEach(t -> {
                if (b.getPosition().equals(t.getPosition())) {
                    boxesOnTarget.add(b);
                }
            });
        });
        return boxesOnTarget;
    }

    @Override
    public final List<Element> getElementsAt(final Position position) {
        return this.elements.stream().filter(e -> e.getPosition().equals(position)).collect(Collectors.toList());
    }

    @Override
    public final boolean moveAttempt(final Element element, final MovementDirection direction) {
        boolean success = false;
        // only users and box can move
        if (element.getType().equals(Type.USER) || element.getType().equals(Type.BOX)) {
            // continue only if target position is inside the grid
            Position newPosition = direction.computeTargetPosition(element.getPosition());
            if (Integer.max(newPosition.getRowIndex(), newPosition.getColumnIndex()) < N_ROWS
                    && Integer.min(newPosition.getRowIndex(), newPosition.getColumnIndex()) >= 0) {
                // find what obstacles are in the target position
                List<Element> obstacles = getElementsAt(newPosition);
                // no obstacles, move
                if (obstacles.isEmpty() || obstacles.stream().allMatch(bo -> bo.getType().equals(Type.TARGET))) {
                    element.setPosition(newPosition);
                    success = true;
                } else {
                    for (Element obstacle : obstacles) {
                        if (element.getType().equals(Type.USER)) {
                            if (obstacle.getType().equals(Type.BOX)) {
                                // this is the user and a box is the obstacle, try to move it
                                boolean boxHasMoved = moveAttempt(obstacle, direction);
                                // the box will move only if its target position is empty
                                if (boxHasMoved) {
                                    // if the box moved, move the user also
                                    element.setPosition(newPosition);
                                    success = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return success;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(this.elements);
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
        GridImpl other = (GridImpl) obj;
        return Objects.equals(this.elements, other.elements);
    }

    @Override
    public final String toString() {
        return "GridImpl [elements=" + this.elements + "]";
    }
}
