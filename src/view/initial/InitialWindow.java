package view.initial;

import java.util.List;

import controller.initial.InitialWindowController;
import view.Window;

/**
 * The initial window.
 */
public interface InitialWindow extends Window {

    /**
     * Sets the controller.
     *
     * @param controller the new controller
     */
    void setController(InitialWindowController controller);

    /**
     * Syncs the windows (e.g. the level list) with the model.
     * 
     * @param levelNames the names of the current levels in the level sequence
     */
    void updateList(List<String> levelNames);

    /**
     * Shows a level invalid dialog.
     *
     * @param cause the cause
     */
    void showLevelInvalidErrorDialog(String cause);

    /**
     * Show level sequence load-error dialog.
     */
    void showDefaultLevelSequenceLoadErrorDialog();

    /**
     * Show level sequence empty error dialog.
     */
    void showLevelSequenceEmptyErrorDialog();

    /**
     * Shows an input output error dialog.
     */
    void showIOErrorDialog();

    /**
     * Shows a class not found error dialog.
     */
    void showClassNotFoundErrorDialog();
}
