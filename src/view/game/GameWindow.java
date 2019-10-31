package view.game;

import model.element.Element;
import view.Window;

/**
 * The "play a level" window.
 */
public interface GameWindow extends Window {

    /**
     * Draws an element in the game canvas.
     *
     * @param element the element to be drawn
     */
    void draw(Element element);

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
