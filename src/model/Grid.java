package model;

import java.io.Serializable; 
import java.util.Collection;

/**
 * The squared-grid-world in which the elements are.
 */
public interface Grid extends Serializable {

    /** The number of rows, which is equal to the number of columns. */
    int N_ROWS = 20;

    /**
     * Adds the given element to the grid. The position information is held inside
     * the element itself.
     *
     * @param element the element to be added to the grid
     */
    void add(Element element);

    /**
     * Removes the given element from the grid.
     *
     * @param element the element to be removed from the grid
     */
    void remove(Element element);

    /**
     * Clears the grid removing all the elements.
     */
    void clear();

    /**
     * Gets all the elements of the grid.
     *
     * @return all the elements of the grid
     */
    Collection<Element> getAllElements();

    /**
     * Gets all the boxes that are on the same position of a target.
     * 
     * @return the boxes on target list
     */
    Collection<Element> getBoxesOnTarget();

    /**
     * Gets all the elements that are currently at the given position. In a given
     * position there can be at most two elements at a given time (e.g. box over
     * target or user over target). So the returned list can contain at most 0 to 2
     * elements.
     *
     * @param position the position to search into
     * @return the elements at the given position
     */
    Collection<Element> getElementsAt(Position position);

    /**
     * Moves, if possible, the given element to the adjacent position in the given
     * direction. The only movable elements are users and boxes. Users can move if
     * the target position is empty or if there is a box that can move in the same
     * direction. A box can move only if the target position is empty or a target.
     *
     * @param element   the element to be moved
     * @param direction the direction of the movement
     * @return true, if the movement succeeded
     */
    boolean moveAttempt(Element element, Direction direction);

    /**
     * The grid hash code is computed on grid elements.
     *
     * @return the computed hash-code
     */
    @Override
    int hashCode();

    /**
     * Two grids are equal if their elements are the same.
     *
     * @param obj the object to be compared
     * @return true, if successful
     */
    @Override
    boolean equals(Object obj);

    /**
     * Creates an empty grid.
     *
     * @return the empty grid
     */
    static Grid createEmpty() {
        return new GridImpl();
    }
}
