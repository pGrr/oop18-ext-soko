package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.Test;
import model.levelsequence.level.grid.Grid;
import model.levelsequence.level.grid.GridImpl;
import model.levelsequence.level.grid.MovementDirection;
import model.levelsequence.level.grid.element.Element;
import model.levelsequence.level.grid.element.ElementImpl;
import model.levelsequence.level.grid.element.PositionImpl;
import model.levelsequence.level.grid.element.Type;

/**
 * Tests {@link Grid}.
 */
public final class TestGrid {

    private final Grid g = new GridImpl();
    private final Element user;
    private final Element box;
    private final Element wall;
    private final Element target;
    private final List<Element> elementList;

    /**
     * Creates a three sample elements, inserts them in a list and creates an empty
     * Grid.
     */
    public TestGrid() {
        this.user = new ElementImpl(Type.USER, new PositionImpl(0, 0), g);
        this.box = new ElementImpl(Type.BOX, new PositionImpl(1, 1), g);
        this.wall = new ElementImpl(Type.WALL, new PositionImpl(2, 2), g);
        this.target = new ElementImpl(Type.TARGET, new PositionImpl(3, 3), g);
        this.elementList = new ArrayList<>();
        this.elementList.add(user);
        this.elementList.add(box);
        this.elementList.add(wall);
        this.elementList.add(target);
        this.g.add(user);
        this.g.add(box);
        this.g.add(wall);
        this.g.add(target);
    }

    /**
     * Test constructors.
     */
    @Test
    public void testCreation() {
        // empty grid
        assertTrue(new GridImpl().getAllElements().isEmpty());
        // grid copy
        Grid g2 = new GridImpl(this.g);
        assertFalse(g2.getAllElements().isEmpty());
        assertEquals(4, g2.getAllElements().stream().count());
        assertTrue(g != g2);
        assertTrue(g.getAllElements() != g2.getAllElements());
        g.getAllElements().forEach(e1 -> {
            g2.getAllElements().forEach(e2 -> {
                assertTrue(e1 != e2);
            });
        });
        IntStream.range(0, 3).forEach(i -> {
            assertTrue(g.getAllElements().get(i).equals(g2.getAllElements().get(i)));
        });
    }

    /**
     * Tests removeElement.
     */
    @Test
    public void testRemoveElement() {
        Grid g2 = new GridImpl(this.g);
        int i = 4;
        assertEquals(i, g2.getAllElements().stream().count());
        g2.remove(user);
        i -= 1;
        assertEquals(i, g2.getAllElements().stream().count());
        g2.remove(box);
        i -= 1;
        assertEquals(i, g2.getAllElements().stream().count());
        g2.remove(wall);
        i -= 1;
        assertEquals(i, g2.getAllElements().stream().count());
        g2.remove(target);
        i -= 1;
        assertEquals(i, g2.getAllElements().stream().count());
        g2.remove(new ElementImpl(Type.EMPTY, new PositionImpl(0, 0), g2));
        assertEquals(i, g2.getAllElements().stream().count());
    }

    /**
     * Tests addElement.
     */
    @Test
    public void testAddElement() {
        Grid g2 = new GridImpl();
        int i = 0;
        assertEquals(i, g2.getAllElements().stream().count());
        g2.add(user);
        i += 1;
        assertEquals(i, g2.getAllElements().stream().count());
        g2.add(box);
        i += 1;
        assertEquals(i, g2.getAllElements().stream().count());
        g2.add(wall);
        i += 1;
        assertEquals(i, g2.getAllElements().stream().count());
        g2.add(target);
        i += 1;
        assertEquals(i, g2.getAllElements().stream().count());
        g2.add(new ElementImpl(Type.EMPTY, new PositionImpl(0, 0), g2));
        i += 1;
        assertEquals(i, g2.getAllElements().stream().count());
    }

    /**
     * Tests clear.
     */
    @Test
    public void testClear() {
        Grid g2 = new GridImpl(g);
        assertEquals(4, g2.getAllElements().stream().count());
        g2.clear();
        assertEquals(0, g2.getAllElements().stream().count());
    }

    /**
     * Tests getAllElements.
     */
    @Test
    public void testGetAllElements() {
        Grid g2 = new GridImpl(g);
        assertEquals(this.elementList, g2.getAllElements());
    }

    /**
     * Tests getBoxesOnTarget.
     */
    @Test
    public void testGetBoxesOnTarget() {
        Grid g2 = new GridImpl(g);
        assertEquals(0, g2.getBoxesOnTarget().stream().count());
        g2.add(new ElementImpl(Type.BOX, new PositionImpl(3, 3), g2));
        assertEquals(1, g2.getBoxesOnTarget().stream().count());
        g2.add(new ElementImpl(Type.TARGET, new PositionImpl(3, 4), g2));
        assertEquals(1, g2.getBoxesOnTarget().stream().count());
        Element b = new ElementImpl(Type.BOX, new PositionImpl(3, 4), g2);
        g2.add(b);
        assertEquals(2, g2.getBoxesOnTarget().stream().count());
        g2.remove(b);
        assertEquals(1, g2.getBoxesOnTarget().stream().count());
    }

    /**
     * Tests getElementsAt.
     */
    @Test
    public void testGetElementsAt() {
        Grid g2 = new GridImpl(g);
        assertEquals(this.user, g2.getElementsAt(new PositionImpl(0, 0)).get(0));
        assertEquals(this.box, g2.getElementsAt(new PositionImpl(1, 1)).get(0));
        assertEquals(this.wall, g2.getElementsAt(new PositionImpl(2, 2)).get(0));
        assertEquals(this.target, g2.getElementsAt(new PositionImpl(3, 3)).get(0));
        g2.add(new ElementImpl(Type.TARGET, new PositionImpl(3, 3), g2));
        assertEquals(2, g2.getElementsAt(new PositionImpl(3, 3)).stream().count());
    }

    /**
     * Tests moveAttempt.
     */
    @Test
    public void testMoveAttempt() {
        Grid g2 = new GridImpl(g);
        Element u = g2.getElementsAt(new PositionImpl(0, 0)).get(0);
        g2.moveAttempt(u, MovementDirection.RIGHT);
        g2.moveAttempt(u, MovementDirection.DOWN);
        g2.moveAttempt(u, MovementDirection.RIGHT);
        g2.moveAttempt(u, MovementDirection.RIGHT);
        g2.moveAttempt(u, MovementDirection.DOWN);
        g2.moveAttempt(u, MovementDirection.DOWN);
        g2.moveAttempt(u, MovementDirection.LEFT);
        g2.moveAttempt(u, MovementDirection.UP);
        assertEquals(new PositionImpl(3, 2), u.getPosition());
        IntStream.range(0, 10).forEach(i -> {
            g2.moveAttempt(u, MovementDirection.LEFT);
        });
        assertEquals(new PositionImpl(3, 0), u.getPosition());
        IntStream.range(0, 10).forEach(i -> {
            g2.moveAttempt(u, MovementDirection.UP);
        });
        assertEquals(new PositionImpl(0, 0), u.getPosition());
    }

    /**
     * Tests equals.
     */
    @Test
    public void testEquals() {
        Grid g1 = new GridImpl(g);
        Grid g2 = new GridImpl(g);
        assertTrue(g2 != g1);
        assertEquals(g2, g1);
        g1.getAllElements().forEach(e1 -> {
            g2.getAllElements().forEach(e2 -> {
                assertTrue(e1 != e2);
            });
        });
        IntStream.range(0, 3).forEach(i -> {
            assertTrue(g1.getAllElements().get(i).equals(g2.getAllElements().get(i)));
        });
    }
}
