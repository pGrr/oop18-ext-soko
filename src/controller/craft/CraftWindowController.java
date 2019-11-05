package controller.craft;

import java.io.IOException;

import model.level.LevelNotValidException;
import model.level.grid.Grid;
import model.level.grid.element.Position;
import model.level.grid.element.Type;

/**
 * The craft window controller.
 */
public interface CraftWindowController {

    /**
     * Clears the model grid and updates the craft view subsequently.
     */
    void clearGrid();

    /**
     * Updates the craft view grid to reflect model's data.
     */
    void updateGrid();

    /**
     * It tries to insert the given type in the given position and then updates the
     * craft window to reflect the changes. The element is inserted in the given
     * position only if the latter is empty or contains an element of a different
     * type. If the position already contains the given type, the position is
     * cleared.
     * 
     * @param type     the type to be inserted
     * @param position the position in which to insert the given type
     */
    void insert(Type type, Position position);

    /**
     * Exits craft level view and goes back to initial view.
     */
    void backToInitialView();

    /**
     * Loads a level from the file-system and updates the craft view with it's grid.
     * 
     * @param path the path of the level on the file-system
     * @throws IOException            an input output exception
     * @throws LevelNotValidException a level not valid exception
     * @throws ClassNotFoundException a class not found exception
     */
    void loadLevel(String path) throws ClassNotFoundException, LevelNotValidException, IOException;

    /**
     * Creates a level with the given name and grid, validates it and saves it in
     * the given absolute path in the file-system.
     * 
     * @param name the name of the level
     * @param grid the grid of the level
     * @param path the absolute path to which save the level in the file-system
     * @throws LevelNotValidException a level not valid exception
     * @throws IOException a input output exception
     */
    void saveLevel(String name, Grid grid, String path) throws LevelNotValidException, IOException;
}
