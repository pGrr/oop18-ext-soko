package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class WindowAbstract.
 */
public abstract class WindowAbstract implements Window {

    /** The component factory. */
    private final GuiComponentFactory componentFactory;

    /** The frame. */
    private final JFrame frame;

    /**
     * Instantiates a new window abstract.
     *
     * @param title                   the title
     * @param heightToScreenSizeRatio the height to screen size ratio
     * @param widthToHeightRatio      the width to height ratio
     */
    public WindowAbstract(String title, double heightToScreenSizeRatio, double widthToHeightRatio) {
        this.componentFactory = GuiComponentFactory.getDefaultInstance();
        this.frame = this.componentFactory.createFrame(title, heightToScreenSizeRatio, widthToHeightRatio);
    }

    /**
     * Show.
     */
    @Override
    public void show() {
        this.getFrame().setVisible(true);
    }

    /**
     * Hide.
     */
    @Override
    public void hide() {
        this.getFrame().setVisible(false);
    }

    /**
     * Close.
     */
    @Override
    public void close() {
        this.getFrame().dispose();
    }

    /**
     * Gets the component factory.
     *
     * @return the component factory
     */
    public GuiComponentFactory getComponentFactory() {
        return this.componentFactory;
    }

    /**
     * Gets the frame.
     *
     * @return the frame
     */
    @Override
    public JFrame getFrame() {
        return this.frame;
    }

    /**
     * Creates the main panel.
     *
     * @return the j panel
     */
    protected abstract JPanel createMainPanel();

}
