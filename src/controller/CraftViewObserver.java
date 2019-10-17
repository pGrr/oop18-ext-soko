package controller;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import model.level.LevelSchema;
import model.level.LevelSchemaImpl.LevelNotValidException;

public class CraftViewObserver {
	
	private static final CraftViewObserver SINGLETON = new CraftViewObserver();
	
	public static final CraftViewObserver getInstance() {
		return SINGLETON;
	}
	
	public void saveLevel(String path, LevelSchema schema) 
			throws LevelNotValidException, FileNotFoundException, IOException {
		try(ObjectOutputStream o = new ObjectOutputStream(
				new BufferedOutputStream(
						new FileOutputStream(path)))) {
			o.writeObject(schema);
		}
	}
	
}
