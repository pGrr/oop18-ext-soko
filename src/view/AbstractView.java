package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToggleButton;
import javax.swing.border.*;

public abstract class AbstractView {
	
	private static final int DEFAULT_PADDING = 20;
	
	private final JFrame frame;

	public AbstractView(String title, double heightToScreenSizeRatio, double widthToHeightRatio) {
		this.frame = frameInitialization(new JFrame(title), heightToScreenSizeRatio, widthToHeightRatio);
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
	
	protected JFrame frameInitialization(JFrame f, double heightToScreenSizeRatio, double widthToHeightRatio) {
		f.setLocationByPlatform(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(computeFrameDimension(heightToScreenSizeRatio, widthToHeightRatio));
		return f;
	}
	
	protected Dimension computeFrameDimension(double heightToScreenSizeRatio, double widthToHeightRatio) {
		double screenSize = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int height = (int)Math.round(screenSize * heightToScreenSizeRatio);
		int width = (int)Math.round(height * widthToHeightRatio);
		return new Dimension(width, height);
	}
	
	protected ImageIcon getImageIcon(String iconPath) {
		return new ImageIcon(ClassLoader.getSystemResource(iconPath));
	}
	
	protected JButton getJButton(String text, ImageIcon icon, ActionListener actionListener) {
		JButton b = new JButton(text, icon);
		b.setBorder(this.getDefaultPaddingBorder(DEFAULT_PADDING));
		b.addActionListener(actionListener);
		return b;
	}
	
	protected JToggleButton getToggleButton(String text, ImageIcon icon, ActionListener actionListener) {
		JToggleButton b = new JToggleButton(text, icon);
		b.setBorder(this.getDefaultPaddingBorder(DEFAULT_PADDING));
		b.addActionListener(actionListener);
		return b;
	}
	
	protected JButton getChoiceButton(String text, String iconPath, ActionListener actionListener) {
		JButton b = new JButton(text, new ImageIcon(ClassLoader.getSystemResource(iconPath)));
		b.setBorder(this.getDefaultPaddingBorder(DEFAULT_PADDING));
		b.addActionListener(actionListener);
		return b;
	}
	
	protected Border getDefaultPaddingBorder(int defaultPadding) {
		return new EmptyBorder(defaultPadding, defaultPadding, defaultPadding, defaultPadding);
	}
}
