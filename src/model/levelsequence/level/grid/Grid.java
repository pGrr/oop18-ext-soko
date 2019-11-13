package model.levelsequence.level.grid;

import java.io.Serializable;
import java.util.List;
import model.levelsequence.level.grid.element.Element;
import model.levelsequence.level.grid.element.Position;

/**
 * The squared grid in which the elements are.
 */
public interface Grid extends Serializable {

    /** The number of rows, which is equal to the number of columns. */
    int N_ROWS = 20;

    /**
     * Adds the given element to the grid. The position information is held by the
     * element itself.
     *
     * @param element the element to be added
     */
    void add(Element element);

    /**
     * Removes the given element from the grid.
     *
     * @param element the element to be removed
     */
    void remove(Element element);

    /**
     * Removes all the elements.
     */
    void clear();

    /**
     * Gets all the elements.
     *
     * @return all the elements of the grid
     */
    List<Element> getAllElements();

    /**
     * Gets all the boxes that are on the same position of a target.
     * 
     * @return the list of the boxes which are on target
     */
    List<Element> getBoxesOnTarget();

    /**
     * Gets all the elements that are currently at the given position. In a given
     * position there can be at most two elements at a given time (e.g. box over
     * target or user over target). So the returned list can contain 0 to 2
     * elements.
     *
     * @param position the position to search into
     * @return the elements in the given position
     */
    List<Element> getElementsAt(Position position);

    /**
     * Moves, if possible, the given element to the adjacent position in the given
     * direction. The only movable elements are users and boxes. Users can move if
     * the target position is empty or if there is a box that can move in the same
     * direction. A box can move only if the target position is empty or a target.
     *
     * @param element   the element to be moved
     * @param direction the direction of the movement
     * @return true, if there has been movement
     */
    boolean moveAttempt(Element element, MovementDirection direction);

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
}
