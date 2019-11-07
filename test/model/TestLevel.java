package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import model.levelsequence.level.Level;
import model.levelsequence.level.LevelImpl;
import model.levelsequence.level.LevelNotValidException;
import model.levelsequence.level.grid.Grid;
import model.levelsequence.level.grid.GridImpl;
import model.levelsequence.level.grid.MovementDirection;
import model.levelsequence.level.grid.element.Element;
import model.levelsequence.level.grid.element.ElementImpl;
import model.levelsequence.level.grid.element.PositionImpl;
import model.levelsequence.level.grid.element.Type;

/**
 * Tests {@link Level}.
 */
public final class TestLevel {

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
    public TestLevel() {
        this.user = new ElementImpl(Type.USER, new PositionImpl(0, 0), g);
        this.box = new ElementImpl(Type.BOX, new PositionImpl(1, 1), g);
        this.target = new ElementImpl(Type.TARGET, new PositionImpl(2, 2), g);
        this.wall = new ElementImpl(Type.WALL, new PositionImpl(3, 3), g);
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
     * Tests getName.
     */
    @Test
    public void testGetName() {
        assertEquals("My name", new LevelImpl("My name", new GridImpl()).getName());
    }

    /**
     * Tests initial level grid compared to current level grid. A level may change
     * its state but its initial state must be preserved in order to be able to
     * recover it.
     */
    @Test
    public void testInitialVsCurrent() {
        Level l = new LevelImpl("My level", new GridImpl(g));
        assertEquals(l.getCurrentGrid(), l.getInitialGrid());
        l.getUser().move(MovementDirection.RIGHT);
        assertNotEquals(l.getCurrentGrid(), l.getInitialGrid());
        l.getUser().move(MovementDirection.LEFT);
        assertEquals(l.getCurrentGrid(), l.getInitialGrid());
    }

    /**
     * Tests getUser.
     */
    @Test
    public void testGetUser() {
        Grid g2 = new GridImpl();
        Element user = new ElementImpl(Type.USER, new PositionImpl(0, 0), g2);
        g2.add(user);
        Level l = new LevelImpl("My level", g2);
        l.getUser().move(MovementDirection.RIGHT);
        assertTrue(l.getUser().getPosition().equals(new PositionImpl(0, 1)));
        assertEquals(l.getUser(), user);
        assertTrue(l.getUser() == user);
        l.getUser().move(MovementDirection.RIGHT);
        assertEquals(l.getUser(), user);
        assertTrue(l.getUser() == user);
    }

    /**
     * Tests isFinished.
     */
    @Test
    public void testIsFinished() {
        Grid g2 = new GridImpl(g);
        g2.add(new ElementImpl(Type.TARGET, new PositionImpl(1, 2), g2));
        g2.add(new ElementImpl(Type.BOX, new PositionImpl(2, 1), g2));
        Level l = new LevelImpl("My level", g2);
        assertFalse(l.isFinished());
        l.getUser().move(MovementDirection.DOWN);
        l.getUser().move(MovementDirection.RIGHT);
        assertFalse(l.isFinished());
        l.getUser().move(MovementDirection.LEFT);
        l.getUser().move(MovementDirection.DOWN);
        l.getUser().move(MovementDirection.RIGHT);
        assertTrue(l.isFinished());
    }

    /**
     * Tests validate.
     */
    @Test
    public void testValidate() {
        // Grid g is an example of a correct grid: must succeed
        Grid g2 = new GridImpl(g);
        Level l2 = new LevelImpl("My level", g2);
        try {
            l2.validate();
        } catch (LevelNotValidException e) {
            fail();
        }
        // empty grid must fail
        Grid g3 = new GridImpl();
        Level l3 = new LevelImpl("My level", g3);
        try {
            l3.validate();
            fail();
        } catch (LevelNotValidException e) {
        }
        // grid with no user must fail
        Grid g4 = new GridImpl();
        g4.add(new ElementImpl(Type.TARGET, new PositionImpl(0, 0), g4));
        Level l4 = new LevelImpl("My level", g4);
        try {
            l4.validate();
            fail();
        } catch (LevelNotValidException e) {
        }
        // grid with no target must fail
        Grid g5 = new GridImpl();
        g5.add(new ElementImpl(Type.USER, new PositionImpl(0, 0), g5));
        Level l5 = new LevelImpl("My level", g4);
        try {
            l5.validate();
            fail();
        } catch (LevelNotValidException e) {
        }
        // grid with more than one user must fail
        Grid g6 = new GridImpl();
        g6.add(new ElementImpl(Type.USER, new PositionImpl(0, 0), g6));
        g6.add(new ElementImpl(Type.USER, new PositionImpl(1, 1), g6));
        Level l6 = new LevelImpl("My level", g6);
        try {
            l6.validate();
            fail();
        } catch (LevelNotValidException e) {
        }
        // grid with an unequal number of targets and boxes must fail
        Grid g7 = new GridImpl();
        g7.add(new ElementImpl(Type.TARGET, new PositionImpl(0, 1), g7));
        g7.add(new ElementImpl(Type.TARGET, new PositionImpl(1, 0), g7));
        g7.add(new ElementImpl(Type.BOX, new PositionImpl(1, 1), g7));
        g7.add(new ElementImpl(Type.BOX, new PositionImpl(0, 2), g7));
        g7.add(new ElementImpl(Type.BOX, new PositionImpl(2, 0), g7));
        g7.add(new ElementImpl(Type.USER, new PositionImpl(2, 2), g7));
        Level l7 = new LevelImpl("My level", g7);
        try {
            l7.validate();
            fail();
        } catch (LevelNotValidException e) {
        }
    }

    /**
     * Tests equals.
     */
    @Test
    public void testEquals() {
        assertNotEquals(new LevelImpl("My name", g), new LevelImpl("My different name", g));
        assertNotEquals(new LevelImpl("My name", g), new LevelImpl("My name", new GridImpl()));
        assertEquals(new LevelImpl("My name", g), new LevelImpl("My name", g));
    }
}
