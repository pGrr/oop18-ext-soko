package view.initial;

import java.util.List;
import controller.initial.InitialWindowController;
import model.levelsequence.level.Level;
import view.Window;

/**
 * The initial view.
 */
public interface InitialWindow extends Window {

    /**
     * Sets the controller.
     *
     * @param controller the new controller
     */
    void setController(InitialWindowController controller);

    /**
     * Updates the level list.
     * 
     * @param levelNames the names of the current levels in the level sequence
     */
    void updateList(List<String> levelNames);

    /**
     * Goes to craft view.
     */
    void toCraftLevelView();

    /**
     * Goes to the game view of the given level.
     * 
     * @param level the level to be played
     */
    void toGameView(Level level);

    /**
     * Shows a level invalid dialog.
     *
     * @param cause the message of the {@link LevelNotValidException}
     */
    void showLevelInvalidErrorDialog(String cause);

    /**
     * Show the default level sequence load-error dialog.
     */
    void showDefaultLevelSequenceLoadErrorDialog();

    /**
     * Show level sequence empty error dialog.
     */
    void showLevelSequenceEmptyErrorDialog();

    /**
     * Shows the input output error dialog.
     */
    void showIOErrorDialog();

    /**
     * Shows the class not found error dialog.
     */
    void showClassNotFoundErrorDialog();
}
