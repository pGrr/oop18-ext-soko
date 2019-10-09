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
import java.util.List;
import model.Element.Type;
import model.Level;
import model.LevelImpl.LevelNotValidException;
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
		this.model.playLevelSequence(levelSequence);
	}

	@Override
	public void playLevel(Level level) {
		this.view.showPlayLevelView(level.getName(), level.getTypeGrid());
	}
	
	@Override
	public void saveLevel(List<List<Type>> typeGrid, String path) 
			throws LevelNotValidException, FileNotFoundException, IOException {
		Level level = model.convertFromTypeGrid(path, typeGrid);
		try(ObjectOutputStream o = new ObjectOutputStream(
				new BufferedOutputStream(
						new FileOutputStream(path)))) {
			o.writeObject(level);
		}
	}

	@Override
	public Level loadLevel(String path) 
			throws LevelNotValidException, ClassNotFoundException, FileNotFoundException, IOException {
		try (ObjectInputStream inputStream = new ObjectInputStream(
				new BufferedInputStream(
						new FileInputStream(path)))) {
			return (Level) inputStream.readObject();
		}
	}
	
	@Override
	public void saveLevelSequence(String name, List<String> levels) 
			throws LevelNotValidException, ClassNotFoundException, IOException {
		try (ObjectOutputStream o = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(name + this.getLevelSequenceFileExtension()))))) {
			o.writeObject(createLevelSequence(name, levels));
		}
	}

	@Override
	public LevelSequence loadLevelSequence(String path) 
			throws IOException, ClassNotFoundException {
		try (ObjectInputStream o = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(path))))) {
			return (LevelSequence) o.readObject();
		}
	}

	@Override
	public LevelSequence createLevelSequence(String name, List<String> paths) 
			throws LevelNotValidException, IOException, ClassNotFoundException {
		LevelSequence levelSequence = new LevelSequenceImpl(name);
		for (String path : paths) {
			levelSequence.add(loadLevel(path));
		}
		return levelSequence;
	}
	
	@Override
	public void moveUp() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void moveDown() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void moveLeft() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void moveRight() {
		// TODO Auto-generated method stub
		
	}
	
}
