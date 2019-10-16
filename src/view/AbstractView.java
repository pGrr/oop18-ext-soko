package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import static view.Views.*;

import java.awt.event.ActionListener;

public abstract  class AbstractView implements View {
	
	private static final String BUTTON_OK_TEXT = "Ok";
	private static final ImageIcon ICON_OK = createImageIcon("icons/ok.png");
		
	private final JFrame frame;
	
	public AbstractView(String title) {
		this.frame = this.createFrame(title);
	}

	public AbstractView(String title, double heightToScreenSizeRatio, double widthToHeightRatio) {
		this.frame = this.createFrame(title);
		this.setFrameAbsoluteDimension(heightToScreenSizeRatio, widthToHeightRatio);
	}

	@Override
	public void show() {
		this.getFrame().setVisible(true);
	}
	
	@Override
	public void hide() {
		this.getFrame().setVisible(false);
	}
	
	@Override
	public void close() {
		this.getFrame().dispose();
	}
	
	@Override
	public void showErrorDialog(String title, String message) {
		createErrorDialog(title, message).setVisible(true);
	}
	
	@Override
	public void showNotifyDialog(String title, String message, ActionListener actionListener) {
		createNotifyDialog(title, message, actionListener).setVisible(true);
	}
	
	private JDialog createErrorDialog(String title, String message) {
		JButton button = createButton(BUTTON_OK_TEXT, ICON_OK, null);
		JDialog dialog = createDialog(this.getFrame(), title, message, button);
		button.addActionListener(errorButtonActionListener(dialog));
		return dialog;
	}
	
	private JDialog createNotifyDialog(String title, String message, ActionListener actionListener) {
		JButton button = createButton(BUTTON_OK_TEXT, ICON_OK, null);
		JDialog dialog = createDialog(this.getFrame(), title, message, button);
		button.addActionListener(actionListener);
		return dialog;
	}
	
	abstract protected ActionListener errorButtonActionListener(JDialog dialog);
	
	public JFrame getFrame() {
		return this.frame;
	}
	
	protected abstract JPanel createMainPanel();
	
	protected final JFrame createFrame(String title) {
		JFrame f = new JFrame(title);
		f.setLocationByPlatform(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return f;
	}
	
	protected final void setFrameAbsoluteDimension(double heightToScreenSizeRatio, double widthToHeightRatio) {
		this.frame.setSize(computeAbsoluteDimension(heightToScreenSizeRatio, widthToHeightRatio));
	}
	
}
