package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import model.level.LevelSchema;
import model.level.LevelSchemaImpl.LevelNotValidException;
import model.sequence.LevelSequence;

public interface ControllerFacade {
		
	void start();
	
	void craftLevel();
	
	void backToInitialView();
	
	String getLevelFileDescription();
	
	String getLevelFileExtension();
	
	String getLevelSequenceFileDescription();
	
	String getLevelSequenceFileExtension();

	void playLevel(LevelSchema levelSchema);
	
	LevelSchema loadLevel(String path) 
			throws LevelNotValidException, ClassNotFoundException, FileNotFoundException, IOException;		
	
	LevelSequence createLevelSequence(String name, List<String> paths) 
			throws LevelNotValidException, IOException, ClassNotFoundException;
	
	void saveLevelSequence(String path, String name, List<String> levels) 
			throws LevelNotValidException, ClassNotFoundException, IOException;
	
	void playLevelSequence(LevelSequence levelSequence);
	
	LevelSequence loadLevelSequence(String path) 
			throws IOException, ClassNotFoundException;
	
	void saveLevel(String path, LevelSchema schema) 
			throws LevelNotValidException, FileNotFoundException, IOException;
	
	void moveUp();
	
	void moveDown();
	
	void moveLeft();
	
	void moveRight();
	
	void levelFinishedAccepted();
	
	void gameFinishedAccepted();
				
}
