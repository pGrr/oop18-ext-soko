package view;

// TODO: Auto-generated Javadoc
/**
 * The Interface InitialWindow.
 */
public interface InitialWindow extends Window {

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
     * @param cause the cause
     */
    void showLevelInvalidDialog(String cause);

    void updateListModel();

}
