package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import model.ModelFacade;
import model.level.LevelInstance;
import model.level.LevelSchema;
import model.level.LevelSchemaImpl.LevelNotValidException;
import view.ViewFacade;

public class CraftViewObserver {
	
	private final ViewFacade view;
	private final ModelFacade model;

	public CraftViewObserver(ViewFacade view, ModelFacade model) {
		this.view = view;
		this.model = model;
	}

	public void playLevel(LevelSchema levelSchema) {
		this.view.showPlayLevelView(levelSchema.getName());
		LevelInstance level = this.model.startLevel(levelSchema, this.view.getPlayableAreaWidth(), this.view.getPlayableAreaHeight());
		this.view.initializePlayView(level.getElements());
	}

	public void saveLevel(String path, LevelSchema schema) 
			throws LevelNotValidException, FileNotFoundException, IOException {
		try(ObjectOutputStream o = new ObjectOutputStream(
				new BufferedOutputStream(
						new FileOutputStream(path)))) {
			o.writeObject(schema);
		}
	}

	public LevelSchema loadLevel(String path) 
			throws LevelNotValidException, ClassNotFoundException, FileNotFoundException, IOException {
		try (ObjectInputStream inputStream = new ObjectInputStream(
				new BufferedInputStream(
						new FileInputStream(path)))) {
			return (LevelSchema) inputStream.readObject();
		}
	}
}
