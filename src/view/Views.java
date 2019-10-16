package view;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
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

public class Views {

	public static final int DEFAULT_PADDING = 20;

	private Views() {} // static class

	public static final Dimension computeAbsoluteDimension(double heightToScreenSizeRatio, double widthToHeightRatio) {
		double screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight();
		int height = (int) Math.round(screenSize * heightToScreenSizeRatio);
		int width = (int) Math.round(height * widthToHeightRatio);
		return new Dimension(width, height);
	}

	public static final JDialog createDialog(JFrame owner, String title, String message, JButton button) {
		JDialog dialog = new JDialog(owner, title);
		JPanel panel = new JPanel(new GridLayout(2,1));
		JLabel label = new JLabel(message);
		label.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING * 2));
		panel.add(label);
		panel.add(button);
		dialog.add(panel);
		dialog.setLocationByPlatform(true);
		dialog.setSize(dialog.getPreferredSize());
		return dialog;
	}

	public static final JFileChooser createFileChooser(String description, String fileExtension) {
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

	public static final JButton createButton(String text, ImageIcon icon, ActionListener actionListener) {
		JButton b = new JButton(text, icon);
		b.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
		b.addActionListener(actionListener);
		return b;
	}

	public static final JButton createButton(String text, String iconPath, ActionListener actionListener) {
		return createButton(text, new ImageIcon(ClassLoader.getSystemResource(iconPath)), actionListener);
	}

	public static final JToggleButton createToggleButton(String text, ImageIcon icon, ActionListener actionListener) {
		JToggleButton b = new JToggleButton(text, icon);
		b.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
		b.addActionListener(actionListener);
		return b;
	}

	public static final JLabel createLabel(String text) {
		JLabel l = new JLabel(text);
		l.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
		return l;
	}

	public static final JList<String> createStringList(List<String> list, int padding) {
		DefaultListModel<String> listModel = new DefaultListModel<>();
		list.forEach(listModel::addElement);
		JList<String> l = new JList<String>(listModel);
		l.setFixedCellHeight(padding);
		return l;
	}

	public static final ImageIcon createImageIcon(String path) {
		return path.isEmpty() ? new ImageIcon() : new ImageIcon(ClassLoader.getSystemResource(path));
	}

	public static final Border createEmptyPaddingBorder(int defaultPadding) {
		return new EmptyBorder(defaultPadding, defaultPadding, defaultPadding, defaultPadding);
	}

	public static final Border createTitledPaddingBorder(String title, int defaultPadding) {
		return new CompoundBorder(new TitledBorder(title), createEmptyPaddingBorder(DEFAULT_PADDING));
	}
	
}
