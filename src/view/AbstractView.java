package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import static view.Views.*;

public abstract  class AbstractView implements View {
		
	private final JFrame frame;

	public AbstractView(String title, double heightToScreenSizeRatio, double widthToHeightRatio) {
		this.frame = createFrame(title, heightToScreenSizeRatio, widthToHeightRatio);
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
		createDialog(this.getFrame(), title, message).setVisible(true);		
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
	
	protected abstract JPanel createMainPanel();

	
}
