package controller.craft;

import java.io.IOException;
import model.levelsequence.level.LevelNotValidException;
import model.levelsequence.level.grid.Grid;
import model.levelsequence.level.grid.element.Position;
import model.levelsequence.level.grid.element.Type;

/**
 * The {@link view.craft.CraftWindow} controller.
 */
public interface CraftWindowController {

    /**
     * Clears the {@link view.craft.CraftWindow} grid.
     */
    void clearGrid();

    /**
     * The element is inserted in the given position of the given the
     * {@link view.craft.CraftWindow} grid, but only if the latter is empty or contains an
     * element of a different type. If the position already contains the given type,
     * the position is cleared.
     * 
     * @param grid     the grid in which to insert
     * @param type     the type to be inserted
     * @param position the position in which to insert
     */
    void insert(Grid grid, Type type, Position position);

    /**
     * Loads a level from the file-system and updates the model and the view subsequently.
     * 
     * @param path the absolute path of the level on the file-system
     * @throws IOException            if an input/output error occurs
     * @throws LevelNotValidException if the level is not valid
     * @throws ClassNotFoundException if the loaded object is not valid (e.g. level
     *                                is corrupted)
     */
    void loadLevel(String path) throws ClassNotFoundException, LevelNotValidException, IOException;

    /**
     * Creates a level with the given name and grid, validates it and saves it using
     * the given absolute path in the file-system.
     * 
     * @param name the name of the level
     * @param grid the grid of the level
     * @param path the absolute path to which save the level in the file-system
     * @throws LevelNotValidException if the level is not valid
     * @throws IOException            if an input/output error occurs
     */
    void saveLevel(String name, Grid grid, String path) throws LevelNotValidException, IOException;
}
