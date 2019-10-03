package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import static view.Views.*;

public abstract class AbstractView {
	
	private final JFrame frame;

	public AbstractView(String title, double heightToScreenSizeRatio, double widthToHeightRatio) {
		this.frame = createFrame(title, heightToScreenSizeRatio, widthToHeightRatio);
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
	
	protected abstract JPanel createMainPanel();
	
}
