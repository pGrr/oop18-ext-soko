package view.craft;

import view.Window;

/**
 * The "Craft a level" window.
 */
public interface CraftWindow extends Window {

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
