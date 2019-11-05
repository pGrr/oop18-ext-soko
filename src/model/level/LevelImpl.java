package model.level;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import model.level.grid.Grid;
import model.level.grid.element.Element;
import model.level.grid.element.Type;

/**
 * An implementation class for the {@link Level} interface.
 */
public class LevelImpl implements Level {

    private static final long serialVersionUID = 2437234480030228409L;

    private final String name;
    private final Grid initialGrid;
    private final Grid currentGrid;
    private Element user;

    /**
     * Instantiates a new LevelImpl with the given name and grid.
     *
     * @param name the name of the level
     * @param grid the grid of the level
     */
    public LevelImpl(final String name, final Grid grid) {
        super();
        this.name = name;
        this.initialGrid = Grid.createCopyOf(grid);
        this.currentGrid = Grid.createCopyOf(grid);
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final Grid getInitialGrid() {
        return initialGrid;
    }

    @Override
    public final Grid getCurrentGrid() {
        return currentGrid;
    }

    @Override
    public final Element getUser() {
        if (this.user == null) {
            this.user = this.currentGrid.getAllElements().stream().filter(e -> e.getType().equals(Type.USER))
                    .findFirst().orElseThrow(IllegalStateException::new);
        }
        return this.user;
    }

    @Override
    public final boolean isFinished() {
        long uncoveredTargets = this.currentGrid.getAllElements().stream()
                .filter(e -> e.getType().equals(Type.BOX) || e.getType().equals(Type.TARGET)).map(Element::getPosition)
                .distinct().count() - countElements(Type.TARGET, this.currentGrid.getAllElements());
        return uncoveredTargets == 0;
    }

    @Override
    public final void validate() throws LevelNotValidException {
        if (!arePositionsValid(currentGrid)) {
            throw new LevelNotValidException.UncorrectPositionException();
        } else {
            long nUsers = countElements(Type.USER, currentGrid.getAllElements());
            long nBoxes = countElements(Type.BOX, currentGrid.getAllElements());
            long nTargets = countElements(Type.TARGET, currentGrid.getAllElements());
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

    @Override
    public final int hashCode() {
        return Objects.hash(currentGrid, name);
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
        LevelImpl other = (LevelImpl) obj;
        return Objects.equals(currentGrid, other.currentGrid) && Objects.equals(name, other.name);
    }

    @Override
    public final String toString() {
        return "LevelImpl [name=" + name + ", grid=" + currentGrid + "]";
    }

    /**
     * Checks if all positions are in a valid range. It finds the maximum and
     * minimum row and column position of all positions, and then checks they are in
     * the correct range, e.g. minimum >= 0 and maximum < number of rows
     * 
     * @param grid the grid containing the elements to be checked
     * @return true, if all positions are valid
     */
    private boolean arePositionsValid(final Grid grid) {
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
     * Counts the elements of a given type in the given collection of elements.
     * 
     * @param type     the type to be counted
     * @param elements the collection of elements in which to count the given type
     * @return the number of elements of the given type in the given collection
     */
    private long countElements(final Type type, final Collection<Element> elements) {
        return elements.stream().filter(e -> e.getType().equals(type)).count();
    }
}
