package controller.craft;

import java.io.IOException;
import java.util.Collection;
import controller.Controller;
import model.levelsequence.level.Level;
import model.levelsequence.level.LevelImpl;
import model.levelsequence.level.LevelNotValidException;
import model.levelsequence.level.grid.Grid;
import model.levelsequence.level.grid.element.Element;
import model.levelsequence.level.grid.element.ElementImpl;
import model.levelsequence.level.grid.element.Position;
import model.levelsequence.level.grid.element.Type;
import view.craft.CraftWindow;

/**
 * An implementation for the {@link CraftWindowController} interface.
 */
public final class CraftWindowControllerImpl implements CraftWindowController {

    private final Controller owner;
    private final CraftWindow view;

    /**
     * Instantiates a new craft window controller with the given controller and
     * view.
     *
     * @param owner the controller, creator of this object
     * @param view  the view to be controlled
     */
    public CraftWindowControllerImpl(final Controller owner, final CraftWindow view) {
        this.owner = owner;
        this.view = view;
    }

    @Override
    public void clearGrid() {
        this.view.clearGrid();
    }

    @Override
    public void insert(final Grid grid, final Type type, final Position position) {
        Collection<Element> elementsInPosition = grid.getElementsAt(position);
        Element newElement = new ElementImpl(type, position, grid);
        if (elementsInPosition.isEmpty()) {
            this.view.addElement(newElement);
        }
        elementsInPosition.forEach(oldElement -> {
            this.view.removeElement(oldElement);
            if (!oldElement.getType().equals(type)) {
                this.view.addElement(newElement);
            }
        });
    }

    @Override
    public void loadLevel(final String path) throws ClassNotFoundException, LevelNotValidException, IOException {
        Grid grid = this.owner.loadLevel(path).getCurrentGrid();
        grid.getAllElements().forEach(this.view::addElement);
    }

    @Override
    public void saveLevel(final String name, final Grid grid, final String path)
            throws LevelNotValidException, IOException {
        Level level = new LevelImpl(name, grid);
        level.validate();
        this.owner.saveLevel(path, level);
    }
}
