package view;

import static view.play.GameConstants.*;
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

public class GuiComponentFactoryImpl implements GuiComponentFactory {

	public static final int DEFAULT_PADDING = 20;
	private static GuiComponentFactory SINGLETON;

	private GuiComponentFactoryImpl() {}
	
	public static GuiComponentFactory getInstance() {
		if (SINGLETON == null) {
			SINGLETON = new GuiComponentFactoryImpl();
		}
		return SINGLETON;
	}

	@Override
	public final Dimension computeAbsoluteDimension(double heightToScreenSizeRatio, double widthToHeightRatio) {
		double screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight();
		int height = (int) Math.round(screenSize * heightToScreenSizeRatio);
		int width = (int) Math.round(height * widthToHeightRatio);
		return new Dimension(width, height);
	}

	@Override
	public final JDialog createDialog(JFrame owner, String title, String message) {
		JDialog dialog = new JDialog(owner, title);
		JPanel panel = new JPanel(new GridLayout(2,1));
		JLabel label = new JLabel(message);
		label.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING * 2));
		panel.add(label);
		dialog.add(panel);
		dialog.setLocationByPlatform(true);
		dialog.setSize(dialog.getPreferredSize());
		return dialog;
	}

	@Override
	public JDialog createNotifyDialog(JFrame owner, String title, String message, ActionListener actionListener) {
		JDialog dialog = new JDialog(owner, title);
		JPanel panel = new JPanel(new GridLayout(2,1));
		JLabel label = new JLabel(message);
		label.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING * 2));
		panel.add(label);
		JButton b = createButton(ERROR_DIALOG_TITLE, "", actionListener);
		panel.add(b);
		b.addActionListener(e -> dialog.dispose());
		dialog.add(panel);
		dialog.setLocationByPlatform(true);
		dialog.setSize(dialog.getPreferredSize());
		return dialog;
	}

	@Override
	public JDialog createNotifyDialog(JFrame owner, String title, String message) {
		return createNotifyDialog(owner, title, message, e -> {});
	}

	@Override
	public final JFileChooser createFileChooser(String description, String fileExtension) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return description;
			}

			@Override
			public boolean accept(File f) {
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
	public JButton createButton(String text) {
		JButton b = new JButton(text);
		b.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
		return b;
	}

	@Override
	public JButton createButton(String text, ImageIcon icon) {
		JButton b = new JButton(text, icon);
		b.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
		return b;
	}

	@Override
	public final JButton createButton(String text, ImageIcon icon, ActionListener actionListener) {
		JButton b = new JButton(text, icon);
		b.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
		b.addActionListener(actionListener);
		return b;
	}

	@Override
	public final JButton createButton(String text, String iconPath, ActionListener actionListener) {
		return createButton(text, new ImageIcon(ClassLoader.getSystemResource(iconPath)), actionListener);
	}

	@Override
	public final JToggleButton createToggleButton(String text, ImageIcon icon, ActionListener actionListener) {
		JToggleButton b = new JToggleButton(text, icon);
		b.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
		b.addActionListener(actionListener);
		return b;
	}

	@Override
	public final JLabel createLabel(String text) {
		JLabel l = new JLabel(text);
		l.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
		return l;
	}

	@Override
	public final JList<String> createStringList(List<String> list, int padding) {
		DefaultListModel<String> listModel = new DefaultListModel<>();
		list.forEach(listModel::addElement);
		JList<String> l = new JList<String>(listModel);
		l.setFixedCellHeight(padding);
		return l;
	}

	@Override
	public final ImageIcon createImageIcon(String path) {
		return path.isEmpty() ? new ImageIcon() : new ImageIcon(ClassLoader.getSystemResource(path));
	}

	@Override
	public ImageIcon createResizedIcon(String path, int w, int h) {
		return createResizedIcon(createImageIcon(path), w, h);
	}
	
	@Override
	public final ImageIcon createResizedIcon(ImageIcon i, int w, int h) {
		Image img = i.getImage();
		return img == null ? new ImageIcon() : new ImageIcon(i.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));	
	}
	
	@Override
	public final Border createEmptyPaddingBorder(int defaultPadding) {
		return new EmptyBorder(defaultPadding, defaultPadding, defaultPadding, defaultPadding);
	}

	@Override
	public final Border createTitledPaddingBorder(String title, int defaultPadding) {
		return new CompoundBorder(new TitledBorder(title), createEmptyPaddingBorder(DEFAULT_PADDING));
	}

	@Override
	public JFrame createFrame(double heightToScreenSizeRatio, double widthToHeightRatio) {
		return createFrame("", heightToScreenSizeRatio, widthToHeightRatio);
	}

	@Override
	public JFrame createFrame(String title, double heightToScreenSizeRatio, double widthToHeightRatio) {
		JFrame f = new JFrame(title);
		f.setSize(computeAbsoluteDimension(heightToScreenSizeRatio, widthToHeightRatio));
		f.setLocationByPlatform(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return f;	
	}

}
