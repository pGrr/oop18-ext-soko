package controller.game;

import java.util.ArrayList;
import java.util.List;
import controller.ControllerFacade;
import model.ModelFacade;
import model.element.Element;
import model.level.LevelSchema;
import view.ViewFacade;

public class GameControllerImpl implements GameController{
	
	private static GameController SINGLETON;

	private GameControllerImpl() {}
	
	public static final GameController getInstance() {
		if (SINGLETON == null) {
			SINGLETON = new GameControllerImpl();
		}
		return SINGLETON;
	}

	@Override
	public void restartCurrentLevel() {
		ControllerFacade.getInstance().playLevel(ModelFacade.getInstance().getCurrentSchema());
	}
	
	@Override
	public void saveGame() {
		List<LevelSchema> l = new ArrayList<>();
		l.add(ModelFacade.getInstance().getCurrentSchema());
		while (ModelFacade.getInstance().hasNextSchema()) {
			l.add(ModelFacade.getInstance().getNextSchema());
		}
		ControllerFacade.getInstance().getSequenceController().saveLevelSequence(l);
	}
		
	@Override
	public void moveUp() {
		List<Element> updatedElements = ModelFacade.getInstance().moveUserUp();
		ViewFacade.getInstance().getGameWindow().drawElements(updatedElements);
		ViewFacade.getInstance().getGameWindow().drawBoxesCoveringTargets(ModelFacade.getInstance().getBoxesOnTargets());
		checkLevelFinished();
	}
	
	@Override
	public void moveDown() {
		List<Element> updatedElements = ModelFacade.getInstance().moveUserDown();
		ViewFacade.getInstance().getGameWindow().drawElements(updatedElements);
		ViewFacade.getInstance().getGameWindow().drawBoxesCoveringTargets(ModelFacade.getInstance().getBoxesOnTargets());
		checkLevelFinished();
	}
	
	@Override
	public void moveLeft() {
		List<Element> updatedElements = ModelFacade.getInstance().moveUserLeft();
		ViewFacade.getInstance().getGameWindow().drawElements(updatedElements);
		ViewFacade.getInstance().getGameWindow().drawBoxesCoveringTargets(ModelFacade.getInstance().getBoxesOnTargets());
		checkLevelFinished();
	}
	
	@Override
	public void moveRight() {
		List<Element> updatedElements = ModelFacade.getInstance().moveUserRight();
		ViewFacade.getInstance().getGameWindow().drawElements(updatedElements);
		ViewFacade.getInstance().getGameWindow().drawBoxesCoveringTargets(ModelFacade.getInstance().getBoxesOnTargets());
		checkLevelFinished();
	}
	
	@Override
	public void levelFinishedAccepted() {
		ControllerFacade.getInstance().playLevel(ModelFacade.getInstance().getNextSchema());
	}
	
	@Override
	public void gameFinishedAccepted() {
		ControllerFacade.getInstance().backToInitialView();
	}
		
	private void checkLevelFinished() {
		if (ModelFacade.getInstance().isLevelFinished()) {
			if (ModelFacade.getInstance().isGameFinished()) {
				ViewFacade.getInstance().getGameWindow().showGameFinishedDialog();
			} else {
				ViewFacade.getInstance().getGameWindow().showLevelFinishedDialog();
			}
		}
	}
}
