package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import model.levelsequence.level.grid.Grid;
import model.levelsequence.level.grid.GridImpl;
import model.levelsequence.level.grid.MovementDirection;
import model.levelsequence.level.grid.element.Element;
import model.levelsequence.level.grid.element.ElementImpl;
import model.levelsequence.level.grid.element.PositionImpl;
import model.levelsequence.level.grid.element.Type;

/**
 * Tests the {@link Element} interface.
 */
public final class TestElement {

    private final Grid g;
    private final Element user;
    private final Element box;
    private final Element target;
    private final Element wall1;
    private final Element wall2;

    /**
     * Creates a sample grid with sample elements.
     */
    public TestElement() {
        this.g = new GridImpl();
        this.user = new ElementImpl(Type.USER, new PositionImpl(0, 0), g);
        this.box = new ElementImpl(Type.BOX, new PositionImpl(1, 1), g);
        this.target = new ElementImpl(Type.TARGET, new PositionImpl(2, 2), g);
        this.wall1 = new ElementImpl(Type.WALL, new PositionImpl(3, 3), g);
        this.wall2 = new ElementImpl(Type.WALL, new PositionImpl(4, 4), g);
        g.add(user);
        g.add(box);
        g.add(target);
        g.add(wall1);
        g.add(wall2);
    }

    /**
     * Tests the getType method.
     */
    @Test
    public void testGetType() {
        assertEquals(Type.USER, user.getType());
        assertEquals(Type.BOX, box.getType());
        assertEquals(Type.TARGET, target.getType());
        assertEquals(Type.WALL, wall1.getType());
        assertEquals(Type.WALL, wall2.getType());
    }

    /**
     * Tests the getPosition method.
     */
    @Test
    public void testGetPosition() {
        assertEquals(new PositionImpl(0, 0), user.getPosition());
        assertEquals(new PositionImpl(1, 1), box.getPosition());
        assertEquals(new PositionImpl(2, 2), target.getPosition());
        assertEquals(new PositionImpl(3, 3), wall1.getPosition());
        assertEquals(new PositionImpl(4, 4), wall2.getPosition());
    }

    /**
     * Tests the setPosition method.
     */
    @Test
    public void testSetPosition() {
        user.setPosition(new PositionImpl(0, 1));
        assertEquals(new PositionImpl(0, 1), user.getPosition());
        user.setPosition(new PositionImpl(0, 0));
        assertEquals(new PositionImpl(0, 0), user.getPosition());
    }

    /**
     * Tests the isTypeMovable method.
     */
    @Test
    public void testIsTypeMovable() {
        assertTrue(user.isTypeMovable());
        assertTrue(box.isTypeMovable());
        assertFalse(target.isTypeMovable());
        assertFalse(wall1.isTypeMovable());
        assertFalse(wall2.isTypeMovable());
    }

    /**
     * Tests the equals method.
     */
    @Test
    public void testEquals() {
        Element user2 = new ElementImpl(Type.USER, new PositionImpl(0, 0), g);
        assertEquals(user, user2);
        user2.setPosition(new PositionImpl(0, 1));
        assertNotEquals(user, user2);
        Element user3 = new ElementImpl(Type.WALL, new PositionImpl(0, 0), g);
        assertNotEquals(user, user3);
        Grid g2 = new GridImpl();
        g2.add(user3);
        Element user4 = new ElementImpl(Type.USER, new PositionImpl(0, 1), g2);
        assertNotEquals(user, user4);
    }

    /**
     * Tests the move method.
     */
    @Test
    public void testMove() {
        // test free movement in all directions using user
        assertEquals(new PositionImpl(0, 0), user.getPosition());
        user.move(MovementDirection.RIGHT);
        assertEquals(new PositionImpl(0, 1), user.getPosition());
        user.move(MovementDirection.DOWN); // user should have pushed box down at 2,1
        assertEquals(new PositionImpl(1, 1), user.getPosition());
        assertEquals(new PositionImpl(2, 1), box.getPosition());
        user.move(MovementDirection.LEFT);
        assertEquals(new PositionImpl(1, 0), user.getPosition());
        user.move(MovementDirection.UP);
        assertEquals(new PositionImpl(0, 0), user.getPosition());
        // test box movement
        box.setPosition(new PositionImpl(1, 1));
        assertEquals(new PositionImpl(1, 1), box.getPosition());
        box.move(MovementDirection.DOWN);
        assertEquals(new PositionImpl(2, 1), box.getPosition());
        // test box over target movement
        box.move(MovementDirection.RIGHT);
        assertEquals(new PositionImpl(2, 2), box.getPosition());
        assertEquals(target.getPosition(), box.getPosition());
        // test box over wall non-movement
        box.move(MovementDirection.RIGHT);
        assertEquals(new PositionImpl(2, 3), box.getPosition());
        box.move(MovementDirection.DOWN);
        assertNotEquals(new PositionImpl(3, 3), box.getPosition());
        assertEquals(new PositionImpl(2, 3), box.getPosition());
        // test user over wall non-movement
        user.move(MovementDirection.DOWN);
        user.move(MovementDirection.DOWN);
        user.move(MovementDirection.DOWN);
        user.move(MovementDirection.RIGHT);
        user.move(MovementDirection.RIGHT);
        user.move(MovementDirection.RIGHT);
        assertNotEquals(new PositionImpl(3, 3), user.getPosition());
        assertEquals(new PositionImpl(3, 2), user.getPosition());
    }
}
