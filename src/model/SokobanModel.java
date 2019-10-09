package model;

import java.util.List;
import model.Element.Type;
import model.LevelImpl.LevelNotValidException;

public interface SokobanModel {
	
	LevelSequence getLevelSequence();
	
	Level convertFromTypeGrid(String name, List<List<Type>> typeGrid) 
			throws LevelNotValidException;
		
	List<List<Type>> convertToTypeGrid(Level level);
	
	void playLevelSequence(LevelSequence levelSequence);

	void setLevelSequence(LevelSequence levelSequence);
	
	void startLevelSequence();
	
	boolean hasNextLevel();
	
	Level getNextLevel();
	
	void moveUserUp();
	
	void moveUserDown();
	
	void moveUserLeft();
	
	void moveUserRight();

}
