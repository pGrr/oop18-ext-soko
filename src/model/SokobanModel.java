package model;

import java.util.List;

public interface SokobanModel {
						
	void startLevel(LevelInstance levelInstance);
	
	List<Element> moveUserUp();
	
	List<Element> moveUserDown();
	
	List<Element> moveUserLeft();
	
	List<Element> moveUserRight();
	
	List<Element> getAllElements();
	
	List<Element> getBoxesOnTargets();
}
