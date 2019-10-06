package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import model.Element.Type;
import model.Level;
import model.LevelImpl.LevelNotValidException;
import model.LevelSequence;
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
		this.model = new SokobanModelImpl(this);
		this.view = new SokobanViewImpl(this);
	}

	@Override
	public void start() {
		this.view.showInitialView();
	}
	
	@Override
	public void craftLevelButtonPressed() {
		this.view.showCraftLevelView();
	}
	
	@Override
	public void backToInitialViewButtonPressed() {
		this.view.showInitialView();
	}
	
	@Override
	public String getLevelFileDescription() {
		return LEVEL_FILE_DESCRIPTION;
	}

	@Override
	public String getLevelFileExtension() {
		return LEVEL_FILE_EXTENSION;
	}

	@Override
	public String getLevelSequenceFileDescription() {
		return LEVEL_SEQUENCE_FILE_DESCRIPTION;
	}

	@Override
	public String getLevelSequenceFileExtension() {
		return LEVEL_SEQUENCE_FILE_EXTENSION;
	}

	@Override
	public void playLevelSequence(LevelSequence levelSequence) {
		// TODO
	}

	@Override
	public void saveLevelButtonPressed(List<List<Type>> typeGrid, String path) 
			throws LevelNotValidException, FileNotFoundException, IOException {
		Level level = model.convertFromTypeGrid(path, typeGrid);
		try(ObjectOutputStream o = new ObjectOutputStream(
				new BufferedOutputStream(
						new FileOutputStream(path)))) {
			o.writeObject(level);
		}
	}

	@Override
	public List<List<Type>> loadLevelButtonPressed(String path) 
			throws LevelNotValidException, ClassNotFoundException, FileNotFoundException, IOException {
		try (ObjectInputStream inputStream = new ObjectInputStream(
				new BufferedInputStream(
						new FileInputStream(path)))) {
			Level level;
			level = (Level) inputStream.readObject();
			return model.convertToTypeGrid(level);
		}
	}
	
}
