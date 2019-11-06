package view.game;

import controller.game.GameWindowController;
import model.levelsequence.level.grid.element.Element;
import view.Window;

/**
 * The "play a level" window.
 */
public interface GameWindow extends Window {

    /**
     * Sets the controller.
     *
     * @param controller the new controller
     */
    void setController(GameWindowController controller);

    /**
     * Draws an element in the game canvas.
     *
     * @param element the element to be drawn
     */
    void draw(Element element);

    /**
     * Hides game window and shows initial window.
     */
    void toInitialView();

    /**
     * Shows the level invalid dialog.
     *
     * @param cause the message of the {@link LevelNotValidException}
     */
    void showLevelInvalidDialog(String cause);

    /**
     * Shows the level finished dialog.
     */
    void showLevelFinishedDialog();

    /**
     * Shows the game finished dialog.
     */
    void showGameFinishedDialog();

    /**
     * Show the class not found error dialog.
     */
    void showClassNotFoundErrorDialog();

    /**
     * Show the input/output error dialog.
     */
    void showIOErrorDialog();
}
