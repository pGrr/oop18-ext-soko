package test.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import model.Model;
import model.ModelImpl;
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
 * Tests the {@link Model}.
 */
public final class TestModel {

    private final Model model;
    private final List<Level> levelList;
    private final Level level1;
    private final Level level2;
    private final Level level3;

    /**
     * Creates a new model, three levels and a list containing those levels.
     */
    public TestModel() {
        this.model = new ModelImpl();
        this.level1 = new LevelImpl("Level 1", new GridImpl());
        this.level2 = new LevelImpl("Level 2", new GridImpl());
        this.level3 = new LevelImpl("Level 3", new GridImpl());
        this.levelList = new ArrayList<>();
        this.levelList.add(this.level1);
        this.levelList.add(this.level2);
        this.levelList.add(this.level3);
    }

    /**
     * Tests the creation and the setting of a level sequence.
     */
    @Test
    public void testAdd() {
        // At creation, the level sequence is empty
        assertTrue(this.model.getCurrentLevelSequenceCurrentState().getAllLevels().isEmpty());
        assertTrue(this.model.getCurrentLevelSequenceInitialState().getAllLevels().isEmpty());
        // but we can set one
        LevelSequence ls = new LevelSequenceImpl("My level sequence", this.levelList);
        this.model.setCurrentLevelSequence(ls);
        assertFalse(this.model.getCurrentLevelSequenceCurrentState().getAllLevels().isEmpty());
        assertFalse(this.model.getCurrentLevelSequenceInitialState().getAllLevels().isEmpty());
        assertEquals(this.level1.getName(), this.model.getLevelNames().get(0));
        assertEquals(this.level2.getName(), this.model.getLevelNames().get(1));
        assertEquals(this.level3.getName(), this.model.getLevelNames().get(2));
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
        LevelSequence ls = new LevelSequenceImpl("My level sequence", this.levelList);
        this.model.setCurrentLevelSequence(ls);
        LevelSequence lsInitialState = this.model.getCurrentLevelSequenceCurrentState().createCopy();
        assertEquals(lsInitialState, this.model.getCurrentLevelSequenceInitialState());
        ls.setNextLevelAsCurrent();
        assertEquals(lsInitialState, this.model.getCurrentLevelSequenceCurrentState());
        ls.setNextLevelAsCurrent();
        Grid currentGrid = this.model.getCurrentLevelSequenceCurrentState().getCurrentLevel().getCurrentGrid();
        currentGrid.add(new ElementImpl(Type.USER, new PositionImpl(0, 0), currentGrid));
        assertNotEquals(lsInitialState, this.model.getCurrentLevelSequenceCurrentState());
        assertEquals(lsInitialState, this.model.getCurrentLevelSequenceInitialState());
    }
}
