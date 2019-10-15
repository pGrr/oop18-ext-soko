package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.Element;
import model.LevelInstance;
import model.LevelSchema;
import model.LevelSchemaImpl.LevelNotValidException;
import model.LevelSequence;
import model.LevelSequenceImpl;
import model.SokobanModel;
import model.SokobanModelImpl;
import view.SokobanView;
import view.SokobanViewImpl;

public class SokobanControllerImpl implements SokobanController {
	
	private static final String LEVEL_SEQUENCE_FILE_DESCRIPTION = "Sokoban level-sequence files (*.sokolevelsequence)";
	private static final String LEVEL_SEQUENCE_FILE_EXTENSION = ".sokolevelsequence";
	private static final String LEVEL_FILE_DESCRIPTION = "Sokoban level files (*.sokolevel)";
	private static final String LEVEL_FILE_EXTENSION = ".sokolevel";
	
	private final SokobanView view;
	private final SokobanModel model;

	public SokobanControllerImpl() {
		this.model = new SokobanModelImpl();
		this.view = new SokobanViewImpl(this);
	}

	@Override
	public void start() {
		this.view.showInitialView();
	}
	
	@Override
	public void craftLevel() {
		this.view.showCraftLevelView();
	}
	
	@Override
	public void backToInitialView() {
		this.view.showInitialView();
	}

	@Override
	public String getLevelSequenceFileExtension() {
		return LEVEL_SEQUENCE_FILE_EXTENSION;
	}

	@Override
	public String getLevelSequenceFileDescription() {
		return LEVEL_SEQUENCE_FILE_DESCRIPTION;
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
	public LevelSequence createLevelSequence(String name, List<String> paths) 
			throws LevelNotValidException, IOException, ClassNotFoundException {		
		List<LevelSchema> levelSchemaList = new ArrayList<>();
		for (String path : paths) {
			levelSchemaList.add(loadLevel(path));
		}
		return new LevelSequenceImpl(name, levelSchemaList);
	}

	@Override
	public void saveLevelSequence(String name, List<String> levels) 
			throws LevelNotValidException, ClassNotFoundException, IOException {
		try (ObjectOutputStream o = new ObjectOutputStream(
				new BufferedOutputStream(
						new FileOutputStream(
								new File(name))))) {
			o.writeObject(createLevelSequence(name, levels));
		}
	}

	@Override
	public LevelSequence loadLevelSequence(String path) 
			throws IOException, ClassNotFoundException {
		try (ObjectInputStream o = new ObjectInputStream(
				new BufferedInputStream(
						new FileInputStream(
								new File(path))))) {
			return (LevelSequence) o.readObject();
		}
	}

	@Override
	public void playLevelSequence(LevelSequence levelSequence) {
		this.model.startLevelSequence(levelSequence);
		if (this.model.hasNextSchema()) {
			LevelSchema levelSchema = this.model.getNextSchema();
			this.playLevel(levelSchema);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public void playLevel(LevelSchema levelSchema) {
		this.view.showPlayLevelView(levelSchema.getName());
		LevelInstance level = this.model.startLevel(levelSchema, this.view.getPlayableAreaWidth(), this.view.getPlayableAreaHeight());
		this.view.initializePlayView(level.getElements());
	}

	@Override
	public void saveLevel(String path, LevelSchema schema) 
			throws LevelNotValidException, FileNotFoundException, IOException {
		try(ObjectOutputStream o = new ObjectOutputStream(
				new BufferedOutputStream(
						new FileOutputStream(path)))) {
			o.writeObject(schema);
		}
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
	public void moveUp() {
		List<Element> updatedElements = this.model.moveUserUp();
		this.view.showElements(updatedElements);
		this.view.showBoxesOnTargets(this.model.getBoxesOnTargets());
		checkLevelFinished();
	}
	
	@Override
	public void moveDown() {
		List<Element> updatedElements = this.model.moveUserDown();
		this.view.showElements(updatedElements);
		this.view.showBoxesOnTargets(this.model.getBoxesOnTargets());
		checkLevelFinished();
	}
	
	@Override
	public void moveLeft() {
		List<Element> updatedElements = this.model.moveUserLeft();
		this.view.showElements(updatedElements);
		this.view.showBoxesOnTargets(this.model.getBoxesOnTargets());
		checkLevelFinished();
	}
	
	@Override
	public void moveRight() {
		List<Element> updatedElements = this.model.moveUserRight();
		this.view.showElements(updatedElements);
		this.view.showBoxesOnTargets(this.model.getBoxesOnTargets());
		checkLevelFinished();
	}

	@Override
	public void updateElements() {
		this.view.initializePlayView(this.model.getAllElements());
	}

	@Override
	public void levelFinishedAccepted() {
		this.playLevel(this.model.getNextSchema());
	}
	
	@Override
	public void gameFinishedAccepted() {
		this.backToInitialView();
	}
		
	private void checkLevelFinished() {
		if (this.model.isLevelFinished()) {
			if (this.model.isGameFinished()) {
				this.view.showGameFinishedDialog();
			} else {
				this.view.showLevelFinishedDialog();
			}
		}
	}

}
