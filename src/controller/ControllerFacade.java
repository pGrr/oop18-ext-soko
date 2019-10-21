package controller;

import controller.game.GameController;
import controller.level.LevelController;
import controller.sequence.SequenceController;
import model.level.LevelSchema;

public interface ControllerFacade {
		
	void start();
	
	void craftLevel();
	
	void playLevel(LevelSchema levelSchema);
	
	void backToInitialView();

	LevelController getLevelController();

	SequenceController getSequenceController();

	GameController getGameController();
	
	static ControllerFacade getInstance() {
		return ControllerFacadeSingleton.getInstance();
	}
	
}
