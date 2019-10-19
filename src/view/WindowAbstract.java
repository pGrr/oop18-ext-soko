package view;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionListener;

public abstract  class WindowAbstract implements Window {
	
	
	private final GuiComponentFactory componentFactory;	
	private final JFrame frame;

	public WindowAbstract(String title, double heightToScreenSizeRatio, double widthToHeightRatio) {
		this.componentFactory = GuiComponentFactory.getDefaultInstance();
		this.frame = this.componentFactory.createFrame(title, heightToScreenSizeRatio, widthToHeightRatio);
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
		componentFactory.createErrorDialog(title, message).setVisible(true);
	}
	
	@Override
	public void showNotifyDialog(String title, String message, ActionListener actionListener) {
		componentFactory.createNotifyDialog(title, message, actionListener).setVisible(true);
	}
	
	public GuiComponentFactory getComponentFactory() {
		return this.componentFactory;
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
		
	abstract protected ActionListener errorAction(JDialog dialog);
	
	protected abstract JPanel createMainPanel();

}
