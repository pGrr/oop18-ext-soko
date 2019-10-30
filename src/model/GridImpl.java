package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

// TODO: Auto-generated Javadoc
/**
 * The Class GridImpl.
 */
public class GridImpl implements Grid {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1944841853255090464L;

    /** The elements. */
    private final Set<Element> elements;

    /**
     * Instantiates a new grid impl.
     */
    public GridImpl() {
        super();
        this.elements = new HashSet<>();
    }

    public GridImpl(Grid grid) {
        this.elements = new HashSet<>();
        grid.getAllElements().forEach(e -> this.elements.add(
                new ElementImpl(
                        e.getType(), 
                        new PositionImpl(e.getPosition().getRowIndex(), e.getPosition().getColumnIndex()),
                        this
                )));
    }

    /**
     * Adds the.
     *
     * @param element the element
     */
    @Override
    public void add(final Element element) {
        this.elements.add(element);
    }

    /**
     * Removes the.
     *
     * @param element the element
     */
    @Override
    public void remove(final Element element) {
        this.elements.remove(element);
    }

    /**
     * Gets the all elements.
     *
     * @return the all elements
     */
    @Override
    public Collection<Element> getAllElements() {
        return new ArrayList<>(elements);
    }
    
    @Override
    public Collection<Element> getBoxesOnTarget() {
        List<Element> boxes = this.elements.stream()
                     .filter(e -> e.getType().equals(Type.BOX))
                     .collect(Collectors.toList());
        List<Element> targets = this.elements.stream()
                                    .filter(e -> e.getType().equals(Type.TARGET))
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
    /**
     * Gets the elements at.
     *
     * @param position the position
     * @return the elements at
     */
    @Override
    public Collection<Element> getElementsAt(final Position position) {
        return this.elements.stream().filter(e -> e.getPosition().equals(position)).collect(Collectors.toList());
    }

    /**
     * Clear.
     */
    @Override
    public void clear() {
        this.elements.clear();
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "GridImpl [elements=" + elements + "]";
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }

    /**
     * Equals.
     *
     * @param obj the obj
     * @return true, if successful
     */
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
        GridImpl other = (GridImpl) obj;
        return Objects.equals(elements, other.elements);
    }

    /**
     * Move attempt.
     *
     * @param element   the element
     * @param direction the direction
     * @return true, if successful
     */
    @Override
    public boolean moveAttempt(final Element element, final Direction direction) {
        boolean success = false;
        if (element.getType().equals(Type.USER) || element.getType().equals(Type.BOX)) {
            Position newPosition = findNewPosition(element.getPosition(), direction);
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

    /**
     * Find new position.
     *
     * @param position  the position
     * @param direction the direction
     * @return the position
     */
    private Position findNewPosition(final Position position, final Direction direction) {
        int r = position.getRowIndex();
        int c = position.getColumnIndex();
        switch (direction) {
        case UP:
            r -= 1;
            break;
        case DOWN:
            r += 1;
            break;
        case LEFT:
            c -= 1;
            break;
        case RIGHT:
            c += 1;
            break;
        default:
            break;
        }
        return new PositionImpl(r, c);
    }

}
