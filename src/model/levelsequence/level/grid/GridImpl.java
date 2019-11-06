package model.levelsequence.level.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import model.levelsequence.level.grid.element.Element;
import model.levelsequence.level.grid.element.ElementImpl;
import model.levelsequence.level.grid.element.Position;
import model.levelsequence.level.grid.element.PositionImpl;
import model.levelsequence.level.grid.element.Type;

/**
 * An implementation class for the {@link Grid} interface.
 */
public class GridImpl implements Grid {

    private static final long serialVersionUID = -1944841853255090464L;
    private final Set<Element> elements;

    /**
     * Instantiates an empty GridImpl object.
     */
    public GridImpl() {
        super();
        this.elements = new HashSet<>();
    }

    /**
     * Instantiates a GridImpl object creating a new grid which is a copy of the
     * given grid.
     * 
     * @param grid the grid
     */
    public GridImpl(final Grid grid) {
        this.elements = new HashSet<>();
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
    public final Collection<Element> getAllElements() {
        return new ArrayList<>(elements);
    }

    @Override
    public final Collection<Element> getBoxesOnTarget() {
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
    public final Collection<Element> getElementsAt(final Position position) {
        return this.elements.stream().filter(e -> e.getPosition().equals(position)).collect(Collectors.toList());
    }

    @Override
    public final boolean moveAttempt(final Element element, final MovementDirection direction) {
        boolean success = false;
        if (element.getType().equals(Type.USER) || element.getType().equals(Type.BOX)) {
            Position newPosition = direction.computeTargetPosition(element.getPosition());
            if (Integer.max(newPosition.getRowIndex(), newPosition.getColumnIndex()) < N_ROWS
                    && Integer.min(newPosition.getRowIndex(), newPosition.getColumnIndex()) >= 0) {
                Collection<Element> obstacles = getElementsAt(newPosition);
                if (obstacles.isEmpty() || obstacles.stream().allMatch(bo -> bo.getType().equals(Type.TARGET))) {
                    element.setPosition(newPosition);
                    success = true;
                } else {
                    for (Element obstacle : obstacles) {
                        if (element.getType().equals(Type.USER)) {
                            if (obstacle.getType().equals(Type.BOX)) {
                                boolean boxHasMoved = moveAttempt(obstacle, direction);
                                if (boxHasMoved) {
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
        return Objects.hash(elements);
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
        return Objects.equals(elements, other.elements);
    }

    @Override
    public final String toString() {
        return "GridImpl [elements=" + elements + "]";
    }
}
