package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.ModelFacade;
import model.level.LevelSchema;
import model.level.LevelSchemaImpl.LevelNotValidException;
import model.sequence.LevelSequence;
import model.sequence.LevelSequenceImpl;

public class InitialViewObserver {
	
	private final ControllerFacade owner;
	private final ModelFacade model;

	public InitialViewObserver(ControllerFacade owner, ModelFacade model) {
		this.owner = owner;
		this.model = model;
	}

	public LevelSequence createLevelSequence(String name, List<String> paths) 
			throws LevelNotValidException, IOException, ClassNotFoundException {		
		List<LevelSchema> levelSchemaList = new ArrayList<>();
		for (String path : paths) {
			levelSchemaList.add(this.owner.loadLevel(path));
		}
		return new LevelSequenceImpl(name, levelSchemaList);
	}

	public void saveLevelSequence(String path, String name, List<String> levels) 
			throws LevelNotValidException, ClassNotFoundException, IOException {
		try (ObjectOutputStream o = new ObjectOutputStream(
				new BufferedOutputStream(
						new FileOutputStream(
								new File(path))))) {
			o.writeObject(createLevelSequence(name, levels));
		}
	}

	public LevelSequence loadLevelSequence(String path) 
			throws IOException, ClassNotFoundException {
		try (ObjectInputStream o = new ObjectInputStream(
				new BufferedInputStream(
						new FileInputStream(
								new File(path))))) {
			return (LevelSequence) o.readObject();
		}
	}

	public void playLevelSequence(LevelSequence levelSequence) {
		this.model.startLevelSequence(levelSequence);
		if (this.model.hasNextSchema()) {
			LevelSchema levelSchema = this.model.getNextSchema();
			this.owner.playLevel(levelSchema);
		} else {
			throw new IllegalArgumentException();
		}
	}
}
