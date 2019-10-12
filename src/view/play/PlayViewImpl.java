package view.play;

import java.util.List;
import javax.swing.JPanel;
import controller.SokobanController;
import model.Element;
import view.AbstractView;

public class PlayViewImpl extends AbstractView implements PlayView {
	
	private static final double HEIGHT_TO_SCREENSIZE_RATIO = 1;
	private static final double WIDTH_TO_HEIGHT_RATIO = 1;
	
	private final SokobanController controller;
	private final LevelPanel levelPanel;
	
	public PlayViewImpl(SokobanController controller, String name) {
		super(name, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
		this.controller = controller;
		this.levelPanel = new LevelPanel(this.getFrame(), this.controller);
		this.getFrame().add(createMainPanel());
		this.getFrame().pack();
	}
	
	@Override
	protected JPanel createMainPanel() {
		return (JPanel) this.levelPanel;
	}

	@Override
	public int getPlayAreaWidth() {
		return this.levelPanel.getWidth();
	}

	@Override
	public int getPlayAreaHeight() {
		return this.levelPanel.getHeight();
	}

	@Override
	public void showElements(List<Element> elements) {
		this.levelPanel.setElements(elements);
	}

}
