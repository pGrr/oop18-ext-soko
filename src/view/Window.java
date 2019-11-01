package view;

import javax.swing.JFrame;

/**
 * A generic Window.
 */
public interface Window {

    /**
     * Shows the window, i.e sets the frame of the window visible.
     */
    void show();

    /**
     * Hides the window, i.e. unsets the frame of the window visible.
     */
    void hide();

    /**
     * Closes the window.
     */
    void close();

    /**
     * Gets the frame of the window.
     *
     * @return the frame
     */
    JFrame getFrame();
}
