package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import static view.Views.*;

public abstract  class AbstractView implements View {
		
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
		createDialog(this.getFrame(), title, message).setVisible(true);		
	}
	
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
