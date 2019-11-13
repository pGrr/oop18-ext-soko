package test.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import org.junit.Test;
import model.levelsequence.LevelSequence;
import model.levelsequence.LevelSequenceImpl;
import model.levelsequence.level.Level;
import model.levelsequence.level.LevelImpl;
import model.levelsequence.level.grid.GridImpl;
import model.levelsequence.level.grid.element.PositionImpl;

/**
 * Tests for {@link LevelSequence}.
 */
public final class TestLevelSequence {

    private final List<Level> levelList;
    private final Level level1;
    private final Level level2;
    private final Level level3;

    /**
     * Creates a level list containing three sample levels.
     */
    public TestLevelSequence() {
        this.levelList = new ArrayList<>();
        this.level1 = new LevelImpl("Level 1", new GridImpl());
        this.level2 = new LevelImpl("Level 2", new GridImpl());
        this.level3 = new LevelImpl("Level 3", new GridImpl());
        this.levelList.add(this.level1);
        this.levelList.add(level2);
        this.levelList.add(level3);
    }

    /**
     * Tests level sequence creation.
     */
    @Test
    public void testCreation() {
        LevelSequence ls = new LevelSequenceImpl("My level-Sequence Name!");
        assertEquals("My level-Sequence Name!", ls.getName());
        assertEquals(ls.getAllLevels(), Collections.emptyList());
        List<Level> levelList2 = new ArrayList<>();
        this.levelList.forEach(levelList2::add);
        ls = new LevelSequenceImpl("", this.levelList);
        assertEquals(levelList2, ls.getAllLevels());
    }

    /**
     * Tests swap method.
     */
    @Test
    public void testSwap() {
        LevelSequence ls = new LevelSequenceImpl("", this.levelList);
        List<Level> levelList2 = new ArrayList<>();
        this.levelList.forEach(levelList2::add);
        ls.swap(0, 2);
        assertNotEquals(levelList2, ls.getAllLevels());
        Collections.swap(levelList2, 0, 2);
        assertEquals(levelList2, ls.getAllLevels());
    }

    /**
     * Tests remove method.
     */
    @Test
    public void testRemove() {
        LevelSequence ls = new LevelSequenceImpl("", this.levelList);
        List<Level> levelList2 = new ArrayList<>();
        this.levelList.forEach(levelList2::add);
        // remove
        ls.remove(1);
        assertNotEquals(levelList2, ls.getAllLevels());
        levelList2.remove(1);
        assertEquals(levelList2, ls.getAllLevels());
        // clear
        ls.clear();
        assertEquals(Collections.emptyList(), ls.getAllLevels());
        assertNotEquals(levelList2, ls.getAllLevels());
        levelList2.clear();
        assertEquals(levelList2, ls.getAllLevels());
        // add
        levelList2.add(this.level1);
        ls.add(this.level1);
        assertEquals(levelList2, ls.getAllLevels());
    }

    /**
     * Tests createCopy method method.
     */
    @Test
    public void testCreateCopy() {
        LevelSequence ls = new LevelSequenceImpl("My level sequence", this.levelList);
        LevelSequence lsCopy = ls.createCopy();
        IntStream.range(0, 2).forEach(i -> {
            assertTrue(ls.getAllLevels().get(i) != lsCopy.getAllLevels().get(i));
        });
    }

    /**
     * Tests the level iteration methods.
     */
    @Test
    public void testLevelIteration() {
        LevelSequence ls = new LevelSequenceImpl("My level sequence", this.levelList).createCopy();
        assertTrue(ls.hasNextLevel());
        ls.setNextLevelAsCurrent();
        assertEquals(ls.getCurrentLevel(), this.level1);
        assertTrue(ls.hasNextLevel());
        ls.setNextLevelAsCurrent();
        assertEquals(ls.getCurrentLevel(), level2);
        assertTrue(ls.hasNextLevel());
        ls.setNextLevelAsCurrent();
        assertEquals(ls.getCurrentLevel(), level3);
        assertFalse(ls.hasNextLevel());
        try {
            ls.setNextLevelAsCurrent();
            fail();
        } catch (NoSuchElementException e) {
        }
    }

    /**
     * Tests restartCurrentLevel method.
     */
    @Test
    public void testLevelRestart() {
        List<Level> levelList2 = new ArrayList<>();
        Level newLevel1 = new LevelImpl("Level 1", new GridImpl());
        levelList2.add(newLevel1);
        LevelSequence ls = new LevelSequenceImpl("My level sequence", levelList2);
        ls.setNextLevelAsCurrent();
        ls.getCurrentLevel().getCurrentGrid().getElementsAt(new PositionImpl(0, 0)).forEach(e -> {
            e.setPosition(new PositionImpl(1, 1));
        });
        ls.getCurrentLevel().getCurrentGrid().getAllElements().forEach(e -> {
            assertFalse(e.getPosition().equals(new PositionImpl(0, 0)));
        });
        ls.restartCurrentLevel();
        ls.getCurrentLevel().getCurrentGrid().getAllElements().forEach(e -> {
            assertTrue(e.getPosition().equals(new PositionImpl(0, 0)));
        });
    }
}
