package controller.craft;

import java.io.IOException;
import java.util.Collection;

import controller.Controller;
import controller.Controllers;
import model.level.Level;
import model.level.LevelImpl;
import model.level.LevelNotValidException;
import model.level.grid.Grid;
import model.level.grid.element.Element;
import model.level.grid.element.ElementImpl;
import model.level.grid.element.Position;
import model.level.grid.element.Type;
import view.craft.CraftWindow;

/**
 * An implementation for the {@link CraftWindowController} interface.
 */
public final class CraftWindowControllerImpl implements CraftWindowController {

    /** The view. */
    private final CraftWindow view;

    /**
     * Instantiates a new craft window controller impl.
     *
     * @param view the view
     */
    public CraftWindowControllerImpl(final CraftWindow view) {
        this.view = view;
    }

    @Override
    public void updateGrid() {
        this.view.updateGrid(Controller.getInstance().getLevelSequenceController().getCurrentLevelSequenceCurrentState()
                .getCurrentLevel().getCurrentGrid());
    }

    @Override
    public void clearGrid() {
        Controller.getInstance().getLevelSequenceController().getCurrentLevelSequenceCurrentState().getCurrentLevel()
                .getCurrentGrid().clear();
        updateGrid();
    }

    @Override
    public void insert(final Type type, final Position position) {
        Grid modelGrid = Controller.getInstance().getLevelSequenceController().getCurrentLevelSequenceCurrentState()
                .getCurrentLevel().getCurrentGrid();
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
    public void backToInitialView() {
        Controller.getInstance().getNavigationController().toInitialView();
    }

    @Override
    public void loadLevel(final String path) throws ClassNotFoundException, LevelNotValidException, IOException {
        Grid grid = Controllers.loadLevel(path).getCurrentGrid();
        this.view.updateGrid(grid);
    }

    @Override
    public void saveLevel(final String name, final Grid grid, final String path) throws LevelNotValidException, IOException {
        Level level = new LevelImpl(name, grid);
        level.validate();
        Controllers.saveLevel(path, level);
    }
}
