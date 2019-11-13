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
     * Clears the craft view grid.
     */
    void clearGrid();

    /**
     * Updates the craft window grid with the given element.
     *
     * @param element the element to be added to the craft window grid
     */
    void addElement(Element element);

    /**
     * Removes the given element from the craft window grid.
     *
     * @param element the element to be removed from the craft window grid
     */
    void removeElement(Element element);

    /**
     * Hides game window and shows initial window.
     */
    void toInitialView();

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
