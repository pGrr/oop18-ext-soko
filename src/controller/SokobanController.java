package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import model.LevelSequence;
import model.Element.Type;
import model.LevelImpl.LevelNotValidException;

public interface SokobanController {
	
	void start();
		
	void craftLevelButtonPressed();
	
	void backToInitialViewButtonPressed();
	
	void playLevelSequence(LevelSequence levelSequence);
	
	String getLevelSequenceFileDescription();
	
	String getLevelSequenceFileExtension();
		
	String getLevelFileDescription();
	
	String getLevelFileExtension();
	
	public List<List<Type>> loadLevelButtonPressed(String path) 
			throws LevelNotValidException, ClassNotFoundException, FileNotFoundException, IOException;
	
	public void saveLevelButtonPressed(List<List<Type>> typeGrid, String path) 
			throws LevelNotValidException, FileNotFoundException, IOException;
	
}
