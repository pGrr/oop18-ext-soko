package model.sequence;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import model.grid.Grid;
import model.grid.GridImpl;
import model.level.Level;
import model.level.LevelImpl;

/**
 * An implementation of the {@link LevelSequence} interface.
 */
public class LevelSequenceImpl implements LevelSequence, Serializable {

    private static final long serialVersionUID = 7771870185440080688L;

    private final String name;
    private final List<Level> levels;
    private transient Iterator<Level> iterator;
    private transient Level currentLevelCurrentState;
    private transient Level currentLevelInitialState;

    /**
     * Instantiates an empty level sequence with a name.
     *
     * @param name the name of the level sequence
     */
    public LevelSequenceImpl(final String name) {
        this.name = name;
        this.levels = new ArrayList<>();
        this.iterator = this.levels.iterator();
        this.currentLevelCurrentState = new LevelImpl("", Grid.createEmpty());
        this.currentLevelInitialState = new LevelImpl("", Grid.createEmpty());
    }

    /**
     * Instantiates a new level sequence with the given name and levels.
     *
     * @param name   the name of the level sequence
     * @param levels the levels to be added to the level sequence
     */
    public LevelSequenceImpl(final String name, final List<Level> levels) {
        this.name = name;
        this.levels = levels;
        this.iterator = levels.iterator();
        this.currentLevelCurrentState = null;
        this.currentLevelInitialState = null;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final List<Level> getAllLevels() {
        return new ArrayList<>(this.levels);
    }

    @Override
    public final void add(final Level level) {
        this.levels.add(level);
        this.iterator = levels.iterator();
    }

    @Override
    public final void swap(final int i, final int j) {
        Collections.swap(levels, i, j);
        this.iterator = levels.iterator();
    }

    @Override
    public final void remove(final int i) {
        levels.remove(i);
        this.iterator = levels.iterator();
    }

    @Override
    public final void clear() {
        levels.clear();
        this.iterator = levels.iterator();
    }

    @Override
    public final boolean hasNextLevel() {
        return iterator.hasNext();
    }

    @Override
    public final void setNextLevelAsCurrent() {
        this.currentLevelCurrentState = iterator.next();
        this.currentLevelInitialState = new LevelImpl(this.currentLevelCurrentState.getName(),
                new GridImpl(this.currentLevelCurrentState.getGrid()));
    }

    @Override
    public final boolean hasCurrentLevel() {
        return this.currentLevelCurrentState != null;
    }

    @Override
    public final Level getCurrentLevel() {
        if (this.currentLevelCurrentState != null) {
            return this.currentLevelCurrentState;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public final void restartCurrentLevel() {
        if (this.currentLevelInitialState != null) {
            this.currentLevelCurrentState = currentLevelInitialState;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public final String toString() {
        return "LevelSequenceImpl [name=" + name + ", levels=" + levels + ", iterator=" + iterator + "]";
    }

    @Override
    public final int hashCode() {
        return Objects.hash(levels, name);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        LevelSequenceImpl other = (LevelSequenceImpl) obj;
        return Objects.equals(levels, other.levels) && Objects.equals(name, other.name);
    }

    /**
     * When a level sequence is loaded from the file-system, it starts the iterator.
     */
    private void readObject(final ObjectInputStream in) throws ClassNotFoundException, IOException {
        in.defaultReadObject();
        this.iterator = levels.iterator();
    }
}
