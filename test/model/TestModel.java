package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import model.levelsequence.LevelSequence;
import model.levelsequence.LevelSequenceImpl;
import model.levelsequence.level.Level;
import model.levelsequence.level.LevelImpl;
import model.levelsequence.level.grid.Grid;
import model.levelsequence.level.grid.GridImpl;
import model.levelsequence.level.grid.element.ElementImpl;
import model.levelsequence.level.grid.element.PositionImpl;
import model.levelsequence.level.grid.element.Type;

/**
 * Tests the {@link Model} interface.
 */
public final class TestModel {

    private final Model model;
    private final List<Level> levelList;
    private final Level l1;
    private final Level l2;
    private final Level l3;

    /**
     * Instantiates a new model, three levels and a list containing those levels.
     */
    public TestModel() {
        this.model = new ModelImpl();
        this.l1 = new LevelImpl("Level 1", new GridImpl());
        this.l2 = new LevelImpl("Level 2", new GridImpl());
        this.l3 = new LevelImpl("Level 3", new GridImpl());
        this.levelList = new ArrayList<>();
        this.levelList.add(l1);
        this.levelList.add(l2);
        this.levelList.add(l3);
    }

    /**
     * Test the creation and the setting of a level sequence.
     */
    @Test
    public void testAdd() {
        // At creation, the level sequence is empty
        assertTrue(model.getCurrentLevelSequenceCurrentState().getAllLevels().isEmpty());
        assertTrue(model.getCurrentLevelSequenceInitialState().getAllLevels().isEmpty());
        // but we can set one
        LevelSequence ls = new LevelSequenceImpl("My level sequence", levelList);
        model.setCurrentLevelSequence(ls);
        assertFalse(model.getCurrentLevelSequenceCurrentState().getAllLevels().isEmpty());
        assertFalse(model.getCurrentLevelSequenceInitialState().getAllLevels().isEmpty());
        assertEquals(l1.getName(), model.getLevelNames().get(0));
        assertEquals(l2.getName(), model.getLevelNames().get(1));
        assertEquals(l3.getName(), model.getLevelNames().get(2));
    }

    /**
     * Tests the preserving of the initial level sequence state even when the state
     * of the current level sequence changes (e.g. when its levels change state).
     * While levels' state inside the current level sequence may change, the
     * initial state of the level sequence is preserved, in order to be able to
     * restore it.
     */
    @Test
    public void testModel() {
        LevelSequence ls = new LevelSequenceImpl("My level sequence", levelList);
        model.setCurrentLevelSequence(ls);
        LevelSequence lsInitialState = model.getCurrentLevelSequenceCurrentState().createCopy();
        assertEquals(lsInitialState, model.getCurrentLevelSequenceInitialState());
        ls.setNextLevelAsCurrent();
        assertEquals(lsInitialState, model.getCurrentLevelSequenceCurrentState());
        ls.setNextLevelAsCurrent();
        Grid currentGrid = model.getCurrentLevelSequenceCurrentState().getCurrentLevel().getCurrentGrid();
        currentGrid.add(new ElementImpl(Type.USER, new PositionImpl(0, 0), currentGrid));
        assertNotEquals(lsInitialState, model.getCurrentLevelSequenceCurrentState());
        assertEquals(lsInitialState, model.getCurrentLevelSequenceInitialState());
    }
}
