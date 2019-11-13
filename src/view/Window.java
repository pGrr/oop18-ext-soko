package view;

/**
 * A generic Window.
 */
public interface Window {

    /**
     * Shows the window, i.e sets the frame of the window visible.
     */
    void show();

    /**
     * Hides the window, i.e. sets the frame of the window not visible.
     */
    void hide();

    /**
     * Closes the window.
     */
    void close();
}
