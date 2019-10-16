package view.play;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import controller.SokobanController;
import model.Element;
import view.AbstractView;

public class PlayViewImpl extends AbstractView implements PlayView {
	
	private static final double HEIGHT_TO_SCREENSIZE_RATIO = 1;
	private static final double WIDTH_TO_HEIGHT_RATIO = 1;
	private static final String MENU_TITLE = "Menu";
	private static final String MENU_BACK_ITEM_TEXT = "Go back to initial view";
	private static final String LEVEL_FINISHED_TITLE = "LEVEL COMPLETE";
	private static final String LEVEL_FINISHED_MESSAGE = "You made it!! Congratulations!";
	private static final String GAME_FINISHED_MESSAGE = "...and that was the last one!! You won!! Congratulations!";
	
	private final SokobanController controller;
	private final PlayViewLevelPanel levelPanel;
	
	public PlayViewImpl(SokobanController controller, String name) {
		super(name, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
		this.controller = controller;
		this.levelPanel = new PlayViewLevelPanel(this.getFrame(), this.controller);
		this.getFrame().setJMenuBar(createMenuBar());
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
		this.levelPanel.showElements(elements);
	}
	
	@Override
	public void showBoxesCoveringTargets(List<Element> boxesCoveringTargets) {
		this.levelPanel.showBoxesOnTargets(boxesCoveringTargets);
	}

	@Override
	public void initialize(List<Element> elements) {
		this.levelPanel.setElements(elements);
	}

	@Override
	protected ActionListener errorButtonActionListener(JDialog dialog) {
		return e -> {
			dialog.dispose();
			this.controller.backToInitialView();
		};
	}
	
	protected ActionListener levelFinishedActionListener() {
		return e -> {
			this.controller.levelFinishedAccepted();
		};
	}
	
	protected ActionListener gameFinishedActionListener() {
		return e -> {
			this.controller.gameFinishedAccepted();
		};
	}
	
	@Override
	public void showLevelFinishedDialog() {
		showNotifyDialog(LEVEL_FINISHED_TITLE, LEVEL_FINISHED_MESSAGE, levelFinishedActionListener());
	}
	
	
	@Override
	public void showGameFinishedDialog() {
		showNotifyDialog(LEVEL_FINISHED_TITLE, GAME_FINISHED_MESSAGE, gameFinishedActionListener());
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu(MENU_TITLE);
		JMenuItem item = new JMenuItem(MENU_BACK_ITEM_TEXT);
		item.addActionListener(e -> this.controller.backToInitialView());
		menu.add(item);
		menuBar.add(menu);
		return menuBar;
	}
}
