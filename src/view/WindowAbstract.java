package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
	
	public GuiComponentFactory getComponentFactory() {
		return this.componentFactory;
	}
	
	@Override
	public JFrame getFrame() {
		return this.frame;
	}
			
	protected abstract JPanel createMainPanel();

}
