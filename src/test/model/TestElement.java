package test.model;

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
 * Tests {@link Element}.
 */
public final class TestElement {

    private final Grid grid;
    private final Element user;
    private final Element box;
    private final Element target;
    private final Element wall1;
    private final Element wall2;

    /**
     * Creates a sample grid with sample elements.
     */
    public TestElement() {
        this.grid = new GridImpl();
        this.user = new ElementImpl(Type.USER, new PositionImpl(0, 0), this.grid);
        this.box = new ElementImpl(Type.BOX, new PositionImpl(1, 1), this.grid);
        this.target = new ElementImpl(Type.TARGET, new PositionImpl(2, 2), this.grid);
        this.wall1 = new ElementImpl(Type.WALL, new PositionImpl(3, 3), this.grid);
        this.wall2 = new ElementImpl(Type.WALL, new PositionImpl(4, 4), this.grid);
        this.grid.add(this.user);
        this.grid.add(this.box);
        this.grid.add(this.target);
        this.grid.add(this.wall1);
        this.grid.add(this.wall2);
    }

    /**
     * Tests the getType method.
     */
    @Test
    public void testGetType() {
        assertEquals(Type.USER, this.user.getType());
        assertEquals(Type.BOX, this.box.getType());
        assertEquals(Type.TARGET, this.target.getType());
        assertEquals(Type.WALL, this.wall1.getType());
        assertEquals(Type.WALL, this.wall2.getType());
    }

    /**
     * Tests the getPosition method.
     */
    @Test
    public void testGetPosition() {
        assertEquals(new PositionImpl(0, 0), this.user.getPosition());
        assertEquals(new PositionImpl(1, 1), this.box.getPosition());
        assertEquals(new PositionImpl(2, 2), this.target.getPosition());
        assertEquals(new PositionImpl(3, 3), this.wall1.getPosition());
        assertEquals(new PositionImpl(4, 4), this.wall2.getPosition());
    }

    /**
     * Tests the setPosition method.
     */
    @Test
    public void testSetPosition() {
        this.user.setPosition(new PositionImpl(0, 1));
        assertEquals(new PositionImpl(0, 1), this.user.getPosition());
        this.user.setPosition(new PositionImpl(0, 0));
        assertEquals(new PositionImpl(0, 0), this.user.getPosition());
    }

    /**
     * Tests the isTypeMovable method.
     */
    @Test
    public void testIsTypeMovable() {
        assertTrue(this.user.isTypeMovable());
        assertTrue(this.box.isTypeMovable());
        assertFalse(this.target.isTypeMovable());
        assertFalse(this.wall1.isTypeMovable());
        assertFalse(this.wall2.isTypeMovable());
    }

    /**
     * Tests the equals method.
     */
    @Test
    public void testEquals() {
        Element user2 = new ElementImpl(Type.USER, new PositionImpl(0, 0), this.grid);
        assertEquals(this.user, user2);
        user2.setPosition(new PositionImpl(0, 1));
        assertNotEquals(this.user, user2);
        Element user3 = new ElementImpl(Type.WALL, new PositionImpl(0, 0), this.grid);
        assertNotEquals(this.user, user3);
        Grid g2 = new GridImpl();
        g2.add(user3);
        Element user4 = new ElementImpl(Type.USER, new PositionImpl(0, 1), g2);
        assertNotEquals(this.user, user4);
    }

    /**
     * Tests the move method.
     */
    @Test
    public void testMove() {
        // test free movement in all directions using user
        assertEquals(new PositionImpl(0, 0), this.user.getPosition());
        this.user.move(MovementDirection.RIGHT);
        assertEquals(new PositionImpl(0, 1), this.user.getPosition());
        this.user.move(MovementDirection.DOWN); // this.user should have pushed box down at 2,1
        assertEquals(new PositionImpl(1, 1), this.user.getPosition());
        assertEquals(new PositionImpl(2, 1), this.box.getPosition());
        this.user.move(MovementDirection.LEFT);
        assertEquals(new PositionImpl(1, 0), this.user.getPosition());
        this.user.move(MovementDirection.UP);
        assertEquals(new PositionImpl(0, 0), this.user.getPosition());
        // test box movement
        this.box.setPosition(new PositionImpl(1, 1));
        assertEquals(new PositionImpl(1, 1), this.box.getPosition());
        this.box.move(MovementDirection.DOWN);
        assertEquals(new PositionImpl(2, 1), this.box.getPosition());
        // test box over target movement
        this.box.move(MovementDirection.RIGHT);
        assertEquals(new PositionImpl(2, 2), this.box.getPosition());
        assertEquals(this.target.getPosition(), this.box.getPosition());
        // test box over wall non-movement
        this.box.move(MovementDirection.RIGHT);
        assertEquals(new PositionImpl(2, 3), this.box.getPosition());
        this.box.move(MovementDirection.DOWN);
        assertNotEquals(new PositionImpl(3, 3), this.box.getPosition());
        assertEquals(new PositionImpl(2, 3), this.box.getPosition());
        // test user over wall non-movement
        this.user.move(MovementDirection.DOWN);
        this.user.move(MovementDirection.DOWN);
        this.user.move(MovementDirection.DOWN);
        this.user.move(MovementDirection.RIGHT);
        this.user.move(MovementDirection.RIGHT);
        this.user.move(MovementDirection.RIGHT);
        assertNotEquals(new PositionImpl(3, 3), this.user.getPosition());
        assertEquals(new PositionImpl(3, 2), this.user.getPosition());
    }
}
