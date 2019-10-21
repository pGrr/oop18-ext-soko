package controller.level;

import java.io.FileNotFoundException;
import java.io.IOException;
import model.level.LevelSchema;
import model.level.LevelSchemaImpl.LevelNotValidException;

public interface LevelController {

	String getLevelFileDescription();
	
	String getLevelFileExtension();
	
	LevelSchema loadLevel(String path) 
			throws LevelNotValidException, ClassNotFoundException, FileNotFoundException, IOException;		

	void saveLevel(String path, LevelSchema schema) 
			throws LevelNotValidException, FileNotFoundException, IOException;
	
	static LevelController getDefaultInstance() {
		return LevelControllerImpl.getInstance();
	}
}
