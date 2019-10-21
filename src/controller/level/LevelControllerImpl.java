package controller.level;

import static controller.ControllerConstants.LEVEL_FILE_DESCRIPTION;
import static controller.ControllerConstants.LEVEL_FILE_EXTENSION;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import model.level.LevelSchema;
import model.level.LevelSchemaImpl.LevelNotValidException;

public class LevelControllerImpl implements LevelController {
	
	private static LevelController SINGLETON;

	private LevelControllerImpl() {}
	
	public static final LevelController getInstance() {
		if (SINGLETON == null) {
			SINGLETON = new LevelControllerImpl();
		}
		return SINGLETON;
	}

	@Override
	public String getLevelFileExtension() {
		return LEVEL_FILE_EXTENSION;
	}

	@Override
	public String getLevelFileDescription() {
		return LEVEL_FILE_DESCRIPTION;
	}
	
	@Override
	public LevelSchema loadLevel(String path) 
			throws LevelNotValidException, ClassNotFoundException, FileNotFoundException, IOException {
		try (ObjectInputStream inputStream = new ObjectInputStream(
				new BufferedInputStream(
						new FileInputStream(path)))) {
			return (LevelSchema) inputStream.readObject();
		}
	}
	
	@Override
	public void saveLevel(String path, LevelSchema schema) throws LevelNotValidException, FileNotFoundException, IOException {
		try(ObjectOutputStream o = new ObjectOutputStream(
				new BufferedOutputStream(
						new FileOutputStream(path)))) {
			o.writeObject(schema);
		}	
	}
}
