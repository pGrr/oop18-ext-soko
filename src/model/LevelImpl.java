package model;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import model.LevelNotValidException;

// TODO: Auto-generated Javadoc
/**
 * The Class LevelImpl.
 */
public class LevelImpl implements Level {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2437234480030228409L;

    /** The name. */
    private final String name;

    /** The grid. */
    private final Grid grid;

    private Element user;

    /**
     * Instantiates a new level impl.
     *
     * @param name the name
     * @param grid the grid
     */
    public LevelImpl(String name, Grid grid) {
        super();
        this.name = name;
        this.grid = grid;
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
     * Gets the grid.
     *
     * @return the grid
     */
    @Override
    public Grid getGrid() {
        return grid;
    }

    /**
     * Gets the user.
     *
     * @return the user
     */
    @Override
    public Element getUser() {
        if (this.user == null) {
            this.user = this.grid.getAllElements().stream().filter(e -> e.getType().equals(Type.USER)).findFirst()
                    .orElseThrow(IllegalStateException::new);
        }
        return this.user;
    }

    /**
     * Validate.
     *
     * @throws LevelNotValidException the level not valid exception
     */
    @Override
    public void validate() throws LevelNotValidException {
        if (!arePositionsValid(grid)) {
            throw new LevelNotValidException.UncorrectPositionException();
        } else {
            long nUsers = countElements(Type.USER, grid.getAllElements());
            long nBoxes = countElements(Type.BOX, grid.getAllElements());
            long nTargets = countElements(Type.TARGET, grid.getAllElements());
            if (nUsers <= 0) {
                throw new LevelNotValidException.NoInitialPointException();
            } else if (nUsers > 1) {
                throw new LevelNotValidException.MultipleInitialPointException();
            } else if (nTargets <= 0) {
                throw new LevelNotValidException.NoTargetException();
            } else if (nTargets != nBoxes) {
                throw new LevelNotValidException.UnequalBoxAndTargetNumberException();
            }
        }
    }

    /**
     * Checks if is finished.
     *
     * @return true, if is finished
     */
    @Override
    public boolean isFinished() {
        long uncoveredTargets = this.grid.getAllElements().stream()
                .filter(e -> e.getType().equals(Type.BOX) || e.getType().equals(Type.TARGET)).map(Element::getPosition)
                .distinct().count() - countElements(Type.TARGET, this.grid.getAllElements());
        return uncoveredTargets == 0;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "LevelImpl [name=" + name + ", grid=" + grid + "]";
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(grid, name);
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
        LevelImpl other = (LevelImpl) obj;
        return Objects.equals(grid, other.grid) && Objects.equals(name, other.name);
    }

    /**
     * Are positions valid.
     *
     * @param grid the grid
     * @return true, if successful
     */
    private boolean arePositionsValid(Grid grid) {
        Optional<Integer> rowMax = grid.getAllElements().stream().map(e -> e.getPosition().getRowIndex())
                .max(Integer::compare);
        Optional<Integer> colMax = grid.getAllElements().stream().map(e -> e.getPosition().getColumnIndex())
                .max(Integer::compare);
        Optional<Integer> rowMin = grid.getAllElements().stream().map(e -> e.getPosition().getRowIndex())
                .min(Integer::compare);
        Optional<Integer> colMin = grid.getAllElements().stream().map(e -> e.getPosition().getColumnIndex())
                .min(Integer::compare);
        if (rowMax.isPresent() && colMax.isPresent() && rowMin.isPresent() && colMin.isPresent()
                && Integer.min(rowMin.get(), colMin.get()) >= 0
                && Integer.max(rowMax.get(), colMax.get()) < Grid.N_ROWS) {
            return true;
        }
        return false;
    }

    /**
     * Count elements.
     *
     * @param type     the type
     * @param elements the elements
     * @return the long
     */
    private long countElements(Type type, Collection<Element> elements) {
        return elements.stream().filter(e -> e.getType().equals(type)).count();
    }
}