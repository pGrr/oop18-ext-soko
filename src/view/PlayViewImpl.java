package view;

import java.util.List;
import java.util.Optional;
import javax.swing.JPanel;
import controller.SokobanController;
import model.Element.Type;

public class PlayViewImpl extends AbstractView implements PlayView {
	
	private static final double HEIGHT_TO_SCREENSIZE_RATIO = 0.9;
	private static final double WIDTH_TO_HEIGHT_RATIO = 1;
	private static final String DEFAULT_TITLE = "Sokoban - Play";
	
	private final SokobanController controller;
	private final Optional<List<List<Type>>> schema;
	
	public PlayViewImpl(SokobanController controller) {
		super(DEFAULT_TITLE, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
		this.controller = controller;
		this.schema = Optional.empty();
		this.getFrame().add(createMainPanel());
		this.getFrame().pack();
	}

	public PlayViewImpl(SokobanController controller, String name, List<List<Type>> schema) {
		super(name, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
		this.controller = controller;
		this.schema = Optional.of(schema);
		this.getFrame().add(createMainPanel());
	}
	
	@Override
	protected JPanel createMainPanel() {
		this.getFrame().setVisible(true);
		return this.schema.isPresent() ? new LevelCanvasImpl(this.getFrame(), this.controller, this.schema.get()) : new JPanel();
	}

}
