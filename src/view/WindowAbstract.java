package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * An abstract class for the {@link Window} interface which implements the
 * Template-method design pattern. It implements the behavior and the
 * initialization steps which are common to all windows and leaves abstract the
 * {@link #createMainPanel()} method which is responsible for the creation of
 * the main panel and all of its components.
 */
public abstract class WindowAbstract implements Window {

    private final GuiComponentFactory componentFactory;
    private final JFrame frame;

    /**
     * Initializes the window providing the {@link GuiComponentFactoryImpl}
     * component factory and creates the main frame with the given
     * height-to-screen-size ratio and width-to-height ratio. This is meant to be a
     * basic template-initialization to be continued by the constructors of
     * sub-classes (using super function).
     *
     * @param title                   the title of the frame
     * @param heightToScreenSizeRatio the height to screen size ratio
     * @param widthToHeightRatio      the width to height ratio
     */
    public WindowAbstract(final String title, final double heightToScreenSizeRatio, final double widthToHeightRatio) {
        this.componentFactory = new GuiComponentFactoryImpl();
        this.frame = this.componentFactory.createFrame(title, heightToScreenSizeRatio, widthToHeightRatio);
    }

    /**
     * {@inheritDoc} Can be overridden to add specific behavior before or after the
     * showing action.
     */
    @Override
    public void show() {
        this.getFrame().setVisible(true);
    }

    /**
     * {@inheritDoc} Can be overridden to add specific behavior before or after the
     * hiding action.
     */
    @Override
    public void hide() {
        this.getFrame().setVisible(false);
    }

    /**
     * {@inheritDoc} Can be overridden to add specific behavior before or after the
     * closing action.
     */
    @Override
    public void close() {
        this.getFrame().dispose();
    }

    /**
     * Gets the JFrame of this Window.
     * 
     * @return the j frame of this window
     */
    public final JFrame getFrame() {
        return this.frame;
    }

    /**
     * Creates and populates the main panel.
     *
     * @return the j panel
     */
    protected abstract JPanel createMainPanel();
}
