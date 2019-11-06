package view.craft;

import controller.craft.CraftWindowController;
import model.levelsequence.level.grid.Grid;
import view.Window;

/**
 * The "Craft a level" window.
 */
public interface CraftWindow extends Window {

    /**
     * Sets the controller.
     *
     * @param controller the new controller
     */
    void setController(CraftWindowController controller);

    /**
     * Updates the craft window grid with the given list of elements.
     *
     * @param grid the new grid
     */
    void updateGrid(Grid grid);

    /**
     * Show IO error dialog.
     */
    void showIOErrorDialog();

    /**
     * Show class not found error dialog.
     */
    void showClassNotFoundErrorDialog();

    /**
     * Show level invalid dialog.
     *
     * @param cause the message of the {@link LevelNotValidException}
     */
    void showLevelInvalidDialog(String cause);
}
