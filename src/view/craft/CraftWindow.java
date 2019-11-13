package view.craft;

import controller.craft.CraftWindowController;
import model.levelsequence.level.grid.element.Element;
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
     * Clears the grid.
     */
    void clearGrid();

    /**
     * Updates the grid with the given element.
     *
     * @param element the element to be added to the craft window grid
     */
    void addElement(Element element);

    /**
     * Removes the given element from the grid.
     *
     * @param element the element to be removed from the grid
     */
    void removeElement(Element element);

    /**
     * Goes back to initial view.
     */
    void toInitialView();

    /**
     * Shows IO error dialog.
     */
    void showIOErrorDialog();

    /**
     * Shows class not found error dialog.
     */
    void showClassNotFoundErrorDialog();

    /**
     * Shows level invalid dialog.
     *
     * @param cause the message of the {@link model.levelsequence.level.LevelNotValidException}
     */
    void showLevelInvalidDialog(String cause);
}
