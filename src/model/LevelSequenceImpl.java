package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class LevelSequenceImpl.
 */
public class LevelSequenceImpl implements LevelSequence, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7771870185440080688L;

    /** The name. */
    private final String name;

    /** The levels. */
    private final List<Level> levels;

    /** The iterator. */
    private transient Iterator<Level> iterator;

    /** The current level. */
    private transient Level currentLevel;

    /**
     * Instantiates a new level sequence impl.
     *
     * @param name the name
     */
    public LevelSequenceImpl(String name) {
        this.name = name;
        this.levels = new ArrayList<>();
        this.iterator = this.levels.iterator();
        this.currentLevel = new LevelImpl("", Grid.createEmpty());
    }

    /**
     * Instantiates a new level sequence impl.
     *
     * @param name   the name
     * @param levels the levels
     */
    public LevelSequenceImpl(String name, List<Level> levels) {
        super();
        this.name = name;
        this.levels = levels;
        this.iterator = levels.iterator();
        this.currentLevel = null;
    }

    /**
     * Read object.
     *
     * @param in the in
     * @throws ClassNotFoundException the class not found exception
     * @throws IOException            Signals that an I/O exception has occurred.
     */
    private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
        in.defaultReadObject();
        this.iterator = levels.iterator();
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets the all levels.
     *
     * @return the all levels
     */
    @Override
    public List<Level> getAllLevels() {
        return Collections.unmodifiableList(levels);
    }

    /**
     * Adds the.
     *
     * @param level the level
     */
    @Override
    public void add(Level level) {
        this.levels.add(level);
        this.iterator = levels.iterator();
    }

    /**
     * Swap.
     *
     * @param i the i
     * @param j the j
     */
    @Override
    public void swap(int i, int j) {
        Collections.swap(levels, i, j);
        this.iterator = levels.iterator();
    }

    /**
     * Removes the.
     *
     * @param i the i
     */
    @Override
    public void remove(int i) {
        levels.remove(i);
        this.iterator = levels.iterator();
    }

    /**
     * Clear.
     */
    @Override
    public void clear() {
        levels.clear();
        this.iterator = levels.iterator();
    }

    /**
     * Checks for next level.
     *
     * @return true, if successful
     */
    @Override
    public boolean hasNextLevel() {
        return iterator.hasNext();
    }

    /**
     * Sets the next level.
     */
    @Override
    public void setNextLevel() {
        this.currentLevel = iterator.next();
    }

    /**
     * Checks for current level.
     *
     * @return true, if successful
     */
    @Override
    public boolean hasCurrentLevel() {
        return this.currentLevel != null;
    }

    /**
     * Gets the current level.
     *
     * @return the current level
     */
    @Override
    public Level getCurrentLevel() {
        if (this.currentLevel != null) {
            return this.currentLevel;
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "LevelSequenceImpl [name=" + name + ", levels=" + levels + ", iterator=" + iterator + "]";
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(levels, name);
    }

    /**
     * Equals.
     *
     * @param obj the obj
     * @return true, if successful
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LevelSequenceImpl other = (LevelSequenceImpl) obj;
        return Objects.equals(levels, other.levels) && Objects.equals(name, other.name);
    }
}
