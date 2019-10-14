package model;

import java.util.List;

public interface LevelInstance {

	int getWidth();
	
	int getHeight();
	
	List<Element> moveUserUp();
	
	List<Element> moveUserDown();
	
	List<Element> moveUserLeft();
	
	List<Element> moveUserRight();

	List<Element> getElements();

	List<Element> getBoxesOnTarget();

}
