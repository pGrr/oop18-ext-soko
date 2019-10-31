package model.level;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import model.element.Element;
import model.element.Type;
import model.grid.Grid;

/**
 * An implementation class for the {@link Level} interface.
 */
public class LevelImpl implements Level {

    private static final long serialVersionUID = 2437234480030228409L;

    private final String name;
    private final Grid grid;
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
        this.grid = grid;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final Grid getGrid() {
        return grid;
    }

    @Override
    public final Element getUser() {
        if (this.user == null) {
            this.user = this.grid.getAllElements().stream().filter(e -> e.getType().equals(Type.USER)).findFirst()
                    .orElseThrow(IllegalStateException::new);
        }
        return this.user;
    }

    @Override
    public final boolean isFinished() {
        long uncoveredTargets = this.grid.getAllElements().stream()
                .filter(e -> e.getType().equals(Type.BOX) || e.getType().equals(Type.TARGET)).map(Element::getPosition)
                .distinct().count() - countElements(Type.TARGET, this.grid.getAllElements());
        return uncoveredTargets == 0;
    }

    @Override
    public final void validate() throws LevelNotValidException {
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

    @Override
    public final int hashCode() {
        return Objects.hash(grid, name);
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
        return Objects.equals(grid, other.grid) && Objects.equals(name, other.name);
    }

    @Override
    public final String toString() {
        return "LevelImpl [name=" + name + ", grid=" + grid + "]";
    }

    /*
     * Checks if all positions are in a valid range. It finds the maximum and
     * minimum row and column position of all positions, and then checks they are in
     * the correct range, e.g. minimum >= 0 and maximum < number of rows
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

    /*
     * Counts the elements of a given type in the given collection of elements.
     */
    private long countElements(final Type type, final Collection<Element> elements) {
        return elements.stream().filter(e -> e.getType().equals(type)).count();
    }
}
