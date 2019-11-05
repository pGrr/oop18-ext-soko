package view;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;

/**
 * An implementation for the {@link GuiComponentFactory} interface. Implements
 * the Singleton design pattern.
 */
public final class GuiComponentFactoryImpl implements GuiComponentFactory {

    /** The default padding size in pixels. */
    public static final int DEFAULT_PADDING = 20;

    private static GuiComponentFactory singleton;

    private GuiComponentFactoryImpl() {
    }

    /**
     * Gets the single instance of GuiComponentFactoryImpl.
     *
     * @return single instance of GuiComponentFactoryImpl
     */
    public static GuiComponentFactory getInstance() {
        if (singleton == null) {
            singleton = new GuiComponentFactoryImpl();
        }
        return singleton;
    }

    @Override
    public JDialog createNotifyDialog(final JFrame owner, final String title, final String message) {
        JDialog dialog = new JDialog(owner, title);
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JLabel label = new JLabel(message);
        label.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
        panel.add(label);
        JButton b = createButton("Ok", "", e -> dialog.dispose());
        panel.add(b);
        dialog.add(panel);
        dialog.setLocationByPlatform(true);
        dialog.setSize(dialog.getPreferredSize());
        return dialog;
    }

    @Override
    public JDialog createActionDialog(final JFrame owner, final String title, final String message,
            final ActionListener actionListener) {
        JDialog dialog = new JDialog(owner, title);
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JLabel label = new JLabel(message);
        label.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
        panel.add(label);
        JButton b = createButton("Ok", "", actionListener);
        b.addActionListener(e -> dialog.dispose());
        panel.add(b);
        dialog.add(panel);
        dialog.setLocationByPlatform(true);
        dialog.setSize(dialog.getPreferredSize());
        return dialog;
    }

    @Override
    public JFileChooser createFileChooser(final String description, final String fileExtension) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {

            @Override
            public String getDescription() {
                return description;
            }

            @Override
            public boolean accept(final File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    String filename = f.getName().toLowerCase();
                    return filename.endsWith(fileExtension);
                }
            }
        });
        return fileChooser;
    }

    @Override
    public JButton createButton(final String text) {
        JButton b = new JButton(text);
        b.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
        return b;
    }

    @Override
    public JButton createButton(final String text, final ImageIcon icon) {
        JButton b = new JButton(text, icon);
        b.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
        return b;
    }

    @Override
    public JButton createButton(final String text, final ImageIcon icon, final ActionListener actionListener) {
        JButton b = new JButton(text, icon);
        b.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
        b.addActionListener(actionListener);
        return b;
    }

    @Override
    public JButton createButton(final String text, final String iconPath, final ActionListener actionListener) {
        return createButton(text, new ImageIcon(ClassLoader.getSystemResource(iconPath)), actionListener);
    }

    @Override
    public JToggleButton createToggleButton(final String text, final ImageIcon icon,
            final ActionListener actionListener) {
        JToggleButton b = new JToggleButton(text, icon);
        b.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
        b.addActionListener(actionListener);
        return b;
    }

    @Override
    public JLabel createLabel(final String text) {
        JLabel l = new JLabel(text);
        l.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
        return l;
    }

    @Override
    public JList<String> createStringList(final List<String> list, final int padding) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        list.forEach(listModel::addElement);
        JList<String> l = new JList<String>(listModel);
        l.setFixedCellHeight(padding);
        return l;
    }

    @Override
    public ImageIcon createImageIcon(final String path) {
        return path.isEmpty() ? new ImageIcon() : new ImageIcon(ClassLoader.getSystemResource(path));
    }

    @Override
    public ImageIcon createResizedIcon(final String path, final int w, final int h) {
        return createResizedIcon(createImageIcon(path), w, h);
    }

    @Override
    public ImageIcon createResizedIcon(final ImageIcon i, final int w, final int h) {
        Image img = i.getImage();
        return img == null ? new ImageIcon() : new ImageIcon(i.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
    }

    @Override
    public Border createEmptyPaddingBorder(final int defaultPadding) {
        return new EmptyBorder(defaultPadding, defaultPadding, defaultPadding, defaultPadding);
    }

    @Override
    public Border createTitledPaddingBorder(final String title, final int defaultPadding) {
        return new CompoundBorder(new TitledBorder(title), createEmptyPaddingBorder(DEFAULT_PADDING));
    }

    @Override
    public JFrame createFrame(final String title, final double heightToScreenSizeRatio,
            final double widthToHeightRatio) {
        JFrame f = new JFrame(title);
        f.setSize(computeAbsoluteDimension(heightToScreenSizeRatio, widthToHeightRatio));
        f.setLocationByPlatform(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return f;
    }

    /**
     * Finds out the screen dimensions and then it computes the absolute dimension
     * with the given height-to-screen-size-ratio and width-to-height-ratio.
     *
     * @param heightToScreenSizeRatio the height to screen size ratio
     * @param widthToHeightRatio      the width to height ratio
     * @return the dimension
     */
    private Dimension computeAbsoluteDimension(final double heightToScreenSizeRatio, final double widthToHeightRatio) {
        double screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight();
        int height = (int) Math.round(screenSize * heightToScreenSizeRatio);
        int width = (int) Math.round(height * widthToHeightRatio);
        return new Dimension(width, height);
    }
}
