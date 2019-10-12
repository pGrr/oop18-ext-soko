package model;

import java.util.List;
import model.Element.Type;
import model.LevelSchemaImpl.LevelNotValidException;

public interface SokobanModel {
						
	void startLevel(LevelInstance levelInstance);
	
	List<Element> moveUserUp();
	
	List<Element> moveUserDown();
	
	List<Element> moveUserLeft();
	
	List<Element> moveUserRight();
}
