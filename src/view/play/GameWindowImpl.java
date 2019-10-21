package view.play;

import java.util.List;
import java.util.Optional;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import controller.ControllerFacade;
import model.element.Element;
import view.GuiComponentFactory;
import view.WindowAbstract;
import static view.play.GameConstants.*;

public class GameWindowImpl extends WindowAbstract implements GameWindow {
		
	private final GameArea gameArea;
	private Optional<GameState> gameState;
	
	public GameWindowImpl(String name) {
		super(name, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
		this.gameArea = new GameArea(this);
		this.gameState = Optional.empty();
		this.getFrame().setJMenuBar(createMenuBar());
		this.getFrame().add(createMainPanel());
		this.getFrame().pack();
	}
	
	@Override
	protected JPanel createMainPanel() {
		return (JPanel) this.gameArea;
	}

	@Override
	public int getGameAreaWidth() {
		return this.gameArea.getWidth();
	}

	@Override
	public int getGameAreaHeight() {
		return this.gameArea.getHeight();
	}

	@Override
	public void drawElements(List<Element> elements) {
		if (!this.gameState.isPresent()) {
			this.gameState = Optional.of(new GameState(elements));
		}
		this.gameArea.drawElements(elements);
	}
	
	@Override
	public void drawBoxesCoveringTargets(List<Element> boxesCoveringTargets) {
		this.gameArea.drawDarkerBoxes(boxesCoveringTargets);
	}
	
	@Override
	public void showLevelFinishedDialog() {
		GuiComponentFactory.getDefaultInstance().createNotifyDialog(this.getFrame(), LEVEL_FINISHED_TITLE, LEVEL_FINISHED_MESSAGE, e -> {
			ControllerFacade.getInstance().getGameController().levelFinishedAccepted();
		}).setVisible(true);
	}
	
	@Override
	public void showGameFinishedDialog() {
		GuiComponentFactory.getDefaultInstance().createNotifyDialog(this.getFrame(), LEVEL_FINISHED_TITLE, GAME_FINISHED_MESSAGE, e -> {
			ControllerFacade.getInstance().getGameController().gameFinishedAccepted();
		}).setVisible(true);
	}
	
	protected GameState getState() {
		requirePresence(this.gameState);
		return this.gameState.get();
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu(MENU_TITLE);
		JMenuItem menuBackItem = new JMenuItem(MENU_BACK_ITEM_TEXT);
		menuBackItem.addActionListener(e -> ControllerFacade.getInstance().backToInitialView());
		JMenuItem restartLevelItem = new JMenuItem(MENU_RESTART_LEVEL_TEXT);
		restartLevelItem.addActionListener(e -> ControllerFacade.getInstance().getGameController().restartCurrentLevel());
		menu.add(menuBackItem);
		menu.add(restartLevelItem);
		menuBar.add(menu);
		return menuBar;
	}
	
	private <T> void requirePresence(Optional<T> o) {
		if (!o.isPresent()) {
			throw new IllegalStateException();
		}
	}
}