package view;

import java.io.File;

import model.element.Element;

// TODO: Auto-generated Javadoc
/**
 * The Interface GameWindow.
 */
public interface GameWindow extends Window {

    /**
     * Draw element.
     *
     * @param element the element
     */
    void draw(Element element);

    /**
     * Show save game file chooser.
     *
     * @return the file
     */
    File showSaveGameFileChooser();

    /**
     * Show level invalid dialog.
     *
     * @param cause the cause
     */
    void showLevelInvalidDialog(String cause);

    /**
     * Show level finished dialog.
     */
    void showLevelFinishedDialog();

    /**
     * Show game finished dialog.
     */
    void showGameFinishedDialog();

    /**
     * Show class not found error dialog.
     */
    void showClassNotFoundErrorDialog();

    /**
     * Show IO error dialog.
     */
    void showIOErrorDialog();

}
