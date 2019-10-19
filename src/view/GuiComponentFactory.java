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

public interface GuiComponentFactory {
	
	Dimension computeAbsoluteDimension(double heightToScreenSizeRatio, double widthToHeightRatio);
	
	JFrame createFrame(double heightToScreenSizeRatio, double widthToHeightRatio);
	
	JFrame createFrame(String title, double heightToScreenSizeRatio, double widthToHeightRatio);
	
	JDialog createDialog(JFrame owner, String title, String message, JButton button);
	
	JFileChooser createFileChooser(String description, String fileExtension);
	
	JButton createButton(String text);
	
	JButton createButton(String text, ImageIcon icon);
	
	JButton createButton(String text, String iconPath, ActionListener actionListener);
	
	JButton createButton(String text, ImageIcon icon, ActionListener actionListener);
	
	JToggleButton createToggleButton(String text, ImageIcon icon, ActionListener actionListener);
	
	JLabel createLabel(String text);
	
	JList<String> createStringList(List<String> list, int padding);
	
	ImageIcon createImageIcon(String path);
	
	ImageIcon createResizedIcon(String path, int w, int h);
	
	ImageIcon createResizedIcon(ImageIcon i, int w, int h);
	
	Border createEmptyPaddingBorder(int defaultPadding);
	
	Border createTitledPaddingBorder(String title, int defaultPadding);
	
	JDialog createErrorDialog(String title, String message);
	
	JDialog createNotifyDialog(String title, String message, ActionListener actionListener);

	static GuiComponentFactory getDefaultInstance() {
		return GuiComponentFactoryImpl.getInstance();
	}
}
