package controller.craft;

import java.io.IOException;
import java.util.Collection;
import controller.Controllers;
import model.Model;
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

    private final CraftWindow view;
    private final Model model;

    /**
     * Instantiates a new craft window controller impl.
     *
     * @param view  the view
     * @param model the model
     */
    public CraftWindowControllerImpl(final Model model, final CraftWindow view) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void updateGrid() {
        this.view.updateGrid(this.model.getCurrentLevelSequenceCurrentState().getCurrentLevel().getCurrentGrid());
    }

    @Override
    public void clearGrid() {
        this.model.getCurrentLevelSequenceCurrentState().getCurrentLevel().getCurrentGrid().clear();
        updateGrid();
    }

    @Override
    public void insert(final Type type, final Position position) {
        Grid modelGrid = this.model.getCurrentLevelSequenceCurrentState().getCurrentLevel().getCurrentGrid();
        Collection<Element> elementsInPosition = modelGrid.getElementsAt(position);
        Element newElement = new ElementImpl(type, position, modelGrid);
        if (elementsInPosition.isEmpty()) {
            modelGrid.add(newElement);
        }
        elementsInPosition.forEach(oldElement -> {
            modelGrid.remove(oldElement);
            if (!oldElement.getType().equals(type)) {
                modelGrid.add(newElement);
            }
        });
        updateGrid();
    }

    @Override
    public void loadLevel(final String path) throws ClassNotFoundException, LevelNotValidException, IOException {
        Grid grid = Controllers.loadLevel(path).getCurrentGrid();
        this.view.updateGrid(grid);
    }

    @Override
    public void saveLevel(final String name, final Grid grid, final String path)
            throws LevelNotValidException, IOException {
        Level level = new LevelImpl(name, grid);
        level.validate();
        Controllers.saveLevel(path, level);
    }
}
