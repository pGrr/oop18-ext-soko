package model.level;

import java.util.List;
import model.element.Element;

public interface LevelInstance {

	int getWidth();
	
	int getHeight();
	
	List<Element> moveUserUp();
	
	List<Element> moveUserDown();
	
	List<Element> moveUserLeft();
	
	List<Element> moveUserRight();

	List<Element> getElements();

	List<Element> getBoxesOnTarget();
	
	boolean isFinished();

}
