package model.levelsequence;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import model.levelsequence.level.Level;
import model.levelsequence.level.LevelImpl;
import model.levelsequence.level.grid.GridImpl;

/**
 * An implementation of {@link LevelSequence}.
 */
public final class LevelSequenceImpl implements LevelSequence, Serializable {

    private static final long serialVersionUID = 7771870185440080688L;

    private final String name;
    private final List<Level> levels;
    private transient Iterator<Level> iterator;
    private transient Level currentLevel;

    /**
     * Creates an empty level sequence with a name.
     *
     * @param name the name of the level sequence
     */
    public LevelSequenceImpl(final String name) {
        this.name = name;
        this.levels = new ArrayList<>();
        this.iterator = this.levels.iterator();
        this.currentLevel = new LevelImpl("", new GridImpl());
    }

    /**
     * Creates a new level sequence using the given name and levels.
     *
     * @param name   the name of the level sequence
     * @param levels the levels to be added to the level sequence
     */
    public LevelSequenceImpl(final String name, final List<Level> levels) {
        this.name = name;
        this.levels = levels;
        this.iterator = this.levels.iterator();
        this.currentLevel = null;
    }

    @Override
    public LevelSequence createCopy() {
        List<Level> levelsCopy = new ArrayList<>();
        this.getAllLevels()
                .forEach(l -> levelsCopy.add(new LevelImpl(new String(l.getName()), new GridImpl(l.getCurrentGrid()))));
        return new LevelSequenceImpl(new String(this.getName()), levelsCopy);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<Level> getAllLevels() {
        return new ArrayList<>(this.levels);
    }

    @Override
    public void add(final Level level) {
        this.levels.add(level);
        this.iterator = this.levels.iterator();
    }

    @Override
    public void swap(final int i, final int j) {
        Collections.swap(this.levels, i, j);
        this.iterator = this.levels.iterator();
    }

    @Override
    public void remove(final int i) {
        this.levels.remove(i);
        this.iterator = this.levels.iterator();
    }

    @Override
    public void clear() {
        this.levels.clear();
        this.iterator = this.levels.iterator();
    }

    @Override
    public boolean hasNextLevel() {
        return this.iterator.hasNext();
    }

    @Override
    public void setNextLevelAsCurrent() {
        this.currentLevel = this.iterator.next();
    }

    @Override
    public Level getCurrentLevel() {
        if (this.currentLevel != null) {
            return this.currentLevel;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void restartCurrentLevel() {
        if (this.currentLevel != null) {
            this.currentLevel = new LevelImpl(this.currentLevel.getName(), this.currentLevel.getInitialGrid());
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public String toString() {
        return "LevelSequenceImpl [name=" + this.name + ", levels=" + this.levels + ", iterator=" + this.iterator + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.levels, this.name);
    }

    @Override
    public boolean equals(final Object obj) {
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
        return Objects.equals(this.levels, other.levels) && Objects.equals(this.name, other.name);
    }

    /**
     * When a level sequence is loaded from the file-system, it starts the iterator.
     * 
     * @param in the input stream
     * @throws ClassNotFoundException the class not found exception
     * @throws IOException            Signals that an I/O exception has occurred.
     */
    private void readObject(final ObjectInputStream in) throws ClassNotFoundException, IOException {
        in.defaultReadObject();
        this.iterator = this.levels.iterator();
    }
}
