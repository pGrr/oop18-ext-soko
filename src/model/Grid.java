package model;

import java.io.Serializable;
import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * The Interface Grid.
 */
public interface Grid extends Serializable {

    /** The n rows. */
    static int N_ROWS = 20;

    /**
     * Adds the.
     *
     * @param element the element
     */
    void add(Element element);

    /**
     * Removes the.
     *
     * @param element the element
     */
    void remove(Element element);

    /**
     * Gets the all elements.
     *
     * @return the all elements
     */
    Collection<Element> getAllElements();

    /**
     * Gets the elements at.
     *
     * @param position the position
     * @return the elements at
     */
    Collection<Element> getElementsAt(Position position);

    /**
     * Clear.
     */
    void clear();

    /**
     * Move attempt.
     *
     * @param element   the element
     * @param direction the direction
     * @return true, if successful
     */
    boolean moveAttempt(Element element, Direction direction);

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

    /**
     * Creates the empty.
     *
     * @return the grid
     */
    static Grid createEmpty() {
        return new GridImpl();
    }
}
