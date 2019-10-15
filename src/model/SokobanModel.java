package model;

import java.util.List;

public interface SokobanModel {
	
	void startLevelSequence(LevelSequence levelSequence);
	
	boolean hasNextSchema();
	
	LevelSchema getNextSchema();
						
	LevelInstance startLevel(LevelSchema levelSchema, int width, int height);
	
	List<Element> moveUserUp();
	
	List<Element> moveUserDown();
	
	List<Element> moveUserLeft();
	
	List<Element> moveUserRight();
	
	List<Element> getAllElements();
	
	List<Element> getBoxesOnTargets();
	
	boolean isLevelFinished();
	
	boolean isGameFinished();
}
