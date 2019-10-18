package view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionListener;

public abstract  class AbstractView implements View {
	
	private static final String BUTTON_OK_TEXT = "Ok";
	private static final String ICON_OK = "icons/ok.png";
	
	private final GuiComponentFactory componentFactory;	
	private final JFrame frame;

	public AbstractView(String title, double heightToScreenSizeRatio, double widthToHeightRatio) {
		this.componentFactory = GuiComponentFactoryImpl.getInstance();
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
		createErrorDialog(title, message).setVisible(true);
	}
	
	@Override
	public void showNotifyDialog(String title, String message, ActionListener actionListener) {
		createNotifyDialog(title, message, actionListener).setVisible(true);
	}
	
	public GuiComponentFactory getComponentFactory() {
		return this.componentFactory;
	}
	
	private JDialog createErrorDialog(String title, String message) {
		JButton button = componentFactory.createButton(BUTTON_OK_TEXT, ICON_OK, null);
		JDialog dialog = componentFactory.createDialog(this.getFrame(), title, message, button);
		button.addActionListener(errorAction(dialog));
		return dialog;
	}
	
	private JDialog createNotifyDialog(String title, String message, ActionListener actionListener) {
		JButton button = componentFactory.createButton(BUTTON_OK_TEXT, ICON_OK, null);
		JDialog dialog = componentFactory.createDialog(this.getFrame(), title, message, button);
		button.addActionListener(actionListener);
		return dialog;
	}
	
	abstract protected ActionListener errorAction(JDialog dialog);
	
	public JFrame getFrame() {
		return this.frame;
	}
	
	protected abstract JPanel createMainPanel();
	
}
