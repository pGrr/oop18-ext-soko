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
	
	String getLevelSequenceFileDescription();
	
	String getLevelSequenceFileExtension();
	
	String getLevelFileDescription();
	
	String getLevelFileExtension();
	
	LevelSequence createLevelSequence(String name, List<String> paths) 
			throws LevelNotValidException, IOException, ClassNotFoundException;
	
	void saveLevelSequence(String path, String name, List<String> levels) 
			throws LevelNotValidException, ClassNotFoundException, IOException;
	
	LevelSequence loadLevelSequence(String path) 
			throws IOException, ClassNotFoundException;
	
	void playLevelSequence(LevelSequence levelSequence);
	
	void saveLevel(String path, LevelSchema schema) 
			throws LevelNotValidException, FileNotFoundException, IOException;

	LevelSchema loadLevel(String path) 
			throws LevelNotValidException, ClassNotFoundException, FileNotFoundException, IOException;		
	
	void moveUp();
	
	void moveDown();
	
	void moveLeft();
	
	void moveRight();
	
	void playLevel(LevelSchema levelSchema);

	void levelFinishedAccepted();

	void gameFinishedAccepted();
}
