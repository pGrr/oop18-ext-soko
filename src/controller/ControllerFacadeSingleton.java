package controller;

import controller.game.GameController;
import controller.level.LevelController;
import controller.sequence.SequenceController;
import model.ModelFacade;
import model.level.LevelInstance;
import model.level.LevelSchema;
import view.ViewFacade;

public class ControllerFacadeSingleton implements ControllerFacade {
	
	private static ControllerFacade SINGLETON;

	private final LevelController levelController;
	private final SequenceController levelSequenceController;
	private final GameController gameController;

	private ControllerFacadeSingleton() {
		this.levelController = LevelController.getDefaultInstance();
		this.levelSequenceController = SequenceController.getDefaultInstance();
		this.gameController = GameController.getDefaultInstance();
	}
	
	public static final ControllerFacade getInstance() {
		if (SINGLETON == null) {
			SINGLETON = new ControllerFacadeSingleton();
		}
		return SINGLETON;
	}

	@Override
	public void start() {
		ViewFacade.getInstance().getInitialWindow().show();
	}
	
	@Override
	public void craftLevel() {
		hideAllWindows();
		ViewFacade.getInstance().getCraftWindow().show();
	}

	@Override
	public void playLevel(LevelSchema levelSchema) {
		hideAllWindows();
		ViewFacade.getInstance().createGameWindow(levelSchema.getName());
		LevelInstance level = ModelFacade.getInstance().initializeLevel(levelSchema, 
				ViewFacade.getInstance().getGameWindow().getGameAreaWidth(), 
				ViewFacade.getInstance().getGameWindow().getGameAreaHeight());
		ViewFacade.getInstance().getGameWindow().show();
		ViewFacade.getInstance().getGameWindow().drawElements(level.getElements());
	}
	
	@Override
	public void backToInitialView() {
		hideAllWindows();
		ViewFacade.getInstance().getInitialWindow().show();
	}

	@Override
	public LevelController getLevelController() {
		return this.levelController;
	}

	@Override
	public SequenceController getSequenceController() {
		return this.levelSequenceController;
	}
	
	@Override
	public GameController getGameController() {
		return this.gameController;
	}
	
	private void hideAllWindows() {
		ViewFacade.getInstance().getAllWindows().forEach(w -> w.hide());
	}
}
