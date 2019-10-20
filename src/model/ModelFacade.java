package model;

import java.util.List;

import model.element.Element;
import model.level.LevelInstance;
import model.level.LevelSchema;
import model.sequence.LevelSequence;

public interface ModelFacade {
	
	void startLevelSequence(LevelSequence levelSequence);
	
	boolean hasNextSchema();
	
	LevelSchema getCurrentSchema();
	
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
	
	static ModelFacade getInstance() {
		return ModelFacadeSingleton.getInstance();
	}
}
