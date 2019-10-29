package view;

import java.awt.Dimension;
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

// TODO: Auto-generated Javadoc
/**
 * A factory for creating GuiComponent objects.
 */
public interface GuiComponentFactory {

    /**
     * Compute absolute dimension.
     *
     * @param heightToScreenSizeRatio the height to screen size ratio
     * @param widthToHeightRatio      the width to height ratio
     * @return the dimension
     */
    Dimension computeAbsoluteDimension(double heightToScreenSizeRatio, double widthToHeightRatio);

    /**
     * Creates a new GuiComponent object.
     *
     * @param heightToScreenSizeRatio the height to screen size ratio
     * @param widthToHeightRatio      the width to height ratio
     * @return the j frame
     */
    JFrame createFrame(double heightToScreenSizeRatio, double widthToHeightRatio);

    /**
     * Creates a new GuiComponent object.
     *
     * @param title                   the title
     * @param heightToScreenSizeRatio the height to screen size ratio
     * @param widthToHeightRatio      the width to height ratio
     * @return the j frame
     */
    JFrame createFrame(String title, double heightToScreenSizeRatio, double widthToHeightRatio);

    /**
     * Creates a new GuiComponent object.
     *
     * @param owner   the owner
     * @param title   the title
     * @param message the message
     * @return the j dialog
     */
    JDialog createDialog(JFrame owner, String title, String message);

    /**
     * Creates a new GuiComponent object.
     *
     * @param owner   the owner
     * @param title   the title
     * @param message the message
     * @return the j dialog
     */
    JDialog createNotifyDialog(JFrame owner, String title, String message);

    /**
     * Creates a new GuiComponent object.
     *
     * @param owner          the owner
     * @param title          the title
     * @param message        the message
     * @param actionListener the action listener
     * @return the j dialog
     */
    JDialog createNotifyDialog(JFrame owner, String title, String message, ActionListener actionListener);

    /**
     * Creates a new GuiComponent object.
     *
     * @param description   the description
     * @param fileExtension the file extension
     * @return the j file chooser
     */
    JFileChooser createFileChooser(String description, String fileExtension);

    /**
     * Creates a new GuiComponent object.
     *
     * @param text the text
     * @return the j button
     */
    JButton createButton(String text);

    /**
     * Creates a new GuiComponent object.
     *
     * @param text the text
     * @param icon the icon
     * @return the j button
     */
    JButton createButton(String text, ImageIcon icon);

    /**
     * Creates a new GuiComponent object.
     *
     * @param text           the text
     * @param iconPath       the icon path
     * @param actionListener the action listener
     * @return the j button
     */
    JButton createButton(String text, String iconPath, ActionListener actionListener);

    /**
     * Creates a new GuiComponent object.
     *
     * @param text           the text
     * @param icon           the icon
     * @param actionListener the action listener
     * @return the j button
     */
    JButton createButton(String text, ImageIcon icon, ActionListener actionListener);

    /**
     * Creates a new GuiComponent object.
     *
     * @param text           the text
     * @param icon           the icon
     * @param actionListener the action listener
     * @return the j toggle button
     */
    JToggleButton createToggleButton(String text, ImageIcon icon, ActionListener actionListener);

    /**
     * Creates a new GuiComponent object.
     *
     * @param text the text
     * @return the j label
     */
    JLabel createLabel(String text);

    /**
     * Creates a new GuiComponent object.
     *
     * @param list    the list
     * @param padding the padding
     * @return the j list< string>
     */
    JList<String> createStringList(List<String> list, int padding);

    /**
     * Creates a new GuiComponent object.
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
     * Creates a new GuiComponent object.
     *
     * @param i the i
     * @param w the w
     * @param h the h
     * @return the image icon
     */
    ImageIcon createResizedIcon(ImageIcon i, int w, int h);

    /**
     * Creates a new GuiComponent object.
     *
     * @param defaultPadding the default padding
     * @return the border
     */
    Border createEmptyPaddingBorder(int defaultPadding);

    /**
     * Creates a new GuiComponent object.
     *
     * @param title          the title
     * @param defaultPadding the default padding
     * @return the border
     */
    Border createTitledPaddingBorder(String title, int defaultPadding);

    /**
     * Gets the default instance.
     *
     * @return the default instance
     */
    static GuiComponentFactory getDefaultInstance() {
        return GuiComponentFactoryImpl.getInstance();
    }
}
