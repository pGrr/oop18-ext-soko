package view;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JToggleButton;
import javax.swing.border.Border;

/**
 * A factory for creating GUI components.
 */
public interface GuiComponentFactory {

    /**
     * Creates a new empty frame with the given height-to-screen-size ratio and
     * width-to-height ratio. This is made to make the frame size relative to the
     * machine-specific screen size.
     *
     * @param title                   the title of the frame
     * @param heightToScreenSizeRatio the height to screen size ratio
     * @param widthToHeightRatio      the width to height ratio
     * @return the j frame
     */
    JFrame createFrame(String title, double heightToScreenSizeRatio, double widthToHeightRatio);

    /**
     * Creates a new dialog with the given title and a message.
     *
     * @param owner   the frame owner of the dialog
     * @param title   the title of the dialog
     * @param message the message of the dialog
     * @return the j dialog
     */
    JDialog createDialog(JFrame owner, String title, String message);

    /**
     * Creates a new dialog with a title, a message and an "OK" button to which it
     * attaches the given action listener.
     *
     * @param owner          the frame owner of the dialog
     * @param title          the title of the dialog
     * @param message        the message of the dialog
     * @param actionListener the action listener to be attached to the ok button
     * @return the j dialog
     */
    JDialog createNotifyDialog(JFrame owner, String title, String message, ActionListener actionListener);

    /**
     * Creates a file chooser with the given file description and file extension.
     *
     * @param fileDescription the file description
     * @param fileExtension   the file extension
     * @return the j file chooser
     */
    JFileChooser createFileChooser(String fileDescription, String fileExtension);

    /**
     * Creates a button with the given text.
     *
     * @param text the text
     * @return the j button
     */
    JButton createButton(String text);

    /**
     * Creates a button with the given text and icon.
     *
     * @param text the text
     * @param icon the icon
     * @return the j button
     */
    JButton createButton(String text, ImageIcon icon);

    /**
     * Creates a button with the given text, the icon (to be loaded from the
     * file-system with the given icon path) and the given action listener.
     *
     * @param text           the text
     * @param iconPath       the icon path
     * @param actionListener the action listener
     * @return the j button
     */
    JButton createButton(String text, String iconPath, ActionListener actionListener);

    /**
     * Creates a button with the given text, the icon and the given action listener.
     * 
     * @param text           the text
     * @param icon           the icon
     * @param actionListener the action listener
     * @return the j button
     */
    JButton createButton(String text, ImageIcon icon, ActionListener actionListener);

    /**
     * Creates a new toggle button with the given text, icon and action listener.
     *
     * @param text           the text
     * @param icon           the icon
     * @param actionListener the action listener
     * @return the j toggle button
     */
    JToggleButton createToggleButton(String text, ImageIcon icon, ActionListener actionListener);

    /**
     * Creates a new label with the given text.
     *
     * @param text the text
     * @return the j label
     */
    JLabel createLabel(String text);

    /**
     * Creates a new JList of strings with the given list of string and padding
     * (expressed in pixels).
     *
     * @param list    the list
     * @param padding the padding
     * @return the j list containing the strings of the list
     */
    JList<String> createStringList(List<String> list, int padding);

    /**
     * Creates a new ImageIcon loading the icon from the given path.
     *
     * @param path the path
     * @return the image icon
     */
    ImageIcon createImageIcon(String path);

    /**
     * Creates a new GuiComponent object.
     *
     * @param path the path
     * @param w    the w
     * @param h    the h
     * @return the image icon
     */
    ImageIcon createResizedIcon(String path, int w, int h);

    /**
     * Creates a resized version of a given ImageIcon object, with the given width
     * and height.
     *
     * @param i the image icon object
     * @param w the width
     * @param h the height
     * @return the resized image icon
     */
    ImageIcon createResizedIcon(ImageIcon i, int w, int h);

    /**
     * Creates a new empty padding border of the given size.
     *
     * @param defaultPadding the default padding, i.e. the empty padding border size
     * @return the border
     */
    Border createEmptyPaddingBorder(int defaultPadding);

    /**
     * Creates a new titled padding border with the given title and size (i.e. padding).
     *
     * @param title          the title of the border
     * @param defaultPadding the default padding, i.e. the border size
     * @return the border
     */
    Border createTitledPaddingBorder(String title, int defaultPadding);

    /**
     * Gets the single instance of the {@link GuiComponentFactory} class.
     *
     * @return the single instance of the {@link GuiComponentFactory} class.
     */
    static GuiComponentFactory getInstance() {
        return GuiComponentFactoryImpl.getInstance();
    }
}
