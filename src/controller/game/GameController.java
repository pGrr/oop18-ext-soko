package controller.game;

public interface GameController {

	void moveUp();
	
	void moveDown();
	
	void moveLeft();
	
	void moveRight();
	
	void restartCurrentLevel();
	
	void levelFinishedAccepted();
	
	void gameFinishedAccepted();
	
	static GameController getDefaultInstance() {
		return GameControllerImpl.getInstance();
	}
	
}
