package view.initial;

import view.Window;

/**
 * The initial window.
 */
public interface InitialWindow extends Window {

    /**
     * Syncs the windows (e.g. the level list) with the model.
     */
    void syncWithModel();

    /**
     * Shows a level invalid dialog.
     *
     * @param cause the cause
     */
    void showLevelInvalidErrorDialog(String cause);

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
