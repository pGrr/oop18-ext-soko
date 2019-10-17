package view.play;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import controller.ControllerFacade;
import model.element.Element;
import view.AbstractGenericView;

public class PlayViewContainer extends AbstractGenericView implements PlayView {
	
	private static final double HEIGHT_TO_SCREENSIZE_RATIO = 1;
	private static final double WIDTH_TO_HEIGHT_RATIO = 1;
	private static final String MENU_TITLE = "Menu";
	private static final String MENU_BACK_ITEM_TEXT = "Go back to initial view";
	private static final String LEVEL_FINISHED_TITLE = "LEVEL COMPLETE";
	private static final String LEVEL_FINISHED_MESSAGE = "You made it!! Congratulations!";
	private static final String GAME_FINISHED_MESSAGE = "...and that was the last one!! You won!! Congratulations!";
	
	private final ControllerFacade controller;
	private final PlayViewGameArea levelPanel;
	
	public PlayViewContainer(ControllerFacade controller, String name) {
		super(name, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
		this.controller = controller;
		this.levelPanel = new PlayViewGameArea(this.getFrame(), this.controller);
		this.getFrame().setJMenuBar(createMenuBar());
		this.getFrame().add(createMainPanel());
		this.getFrame().pack();
	}
	
	@Override
	protected JPanel createMainPanel() {
		return (JPanel) this.levelPanel;
	}

	@Override
	public int getGameAreaWidth() {
		return this.levelPanel.getWidth();
	}

	@Override
	public int getGameAreaHeight() {
		return this.levelPanel.getHeight();
	}

	@Override
	public void drawElements(List<Element> elements) {
		this.levelPanel.drawElements(elements);
	}
	
	@Override
	public void drawBoxesCoveringTargets(List<Element> boxesCoveringTargets) {
		this.levelPanel.drawDarkerBoxes(boxesCoveringTargets);
	}

	@Override
	public void initialize(List<Element> elements) {
		this.levelPanel.setElements(elements);
	}

	@Override
	protected ActionListener errorAction(JDialog dialog) {
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
