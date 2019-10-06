package view;

import javax.swing.JPanel;
import controller.SokobanController;

public class PlayViewImpl extends AbstractView implements PlayView {
	
	private static final double HEIGHT_TO_SCREENSIZE_RATIO = 1;
	private static final double WIDTH_TO_HEIGHT_RATIO = 1;
	private static final String TITLE = "Play level";
	
	SokobanController controller;

	public PlayViewImpl(SokobanController controller) {
		super(TITLE, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
		this.controller = controller;
	}
	
	@Override
	protected JPanel createMainPanel() {
		// TODO Auto-generated method stub
		return null;
	}

}
