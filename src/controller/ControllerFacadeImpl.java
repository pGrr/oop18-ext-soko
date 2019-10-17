package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import model.level.LevelSchema;
import model.level.LevelSchemaImpl.LevelNotValidException;
import model.sequence.LevelSequence;
import model.ModelFacade;
import model.ModelFacadeImpl;
import view.ViewFacade;
import view.ViewFacadeImpl;

public class ControllerFacadeImpl implements ControllerFacade {
	
	private static final String LEVEL_SEQUENCE_FILE_DESCRIPTION = "Sokoban level-sequence files (*.sokolevelsequence)";
	private static final String LEVEL_SEQUENCE_FILE_EXTENSION = ".sokolevelsequence";
	private static final String LEVEL_FILE_DESCRIPTION = "Sokoban level files (*.sokolevel)";
	private static final String LEVEL_FILE_EXTENSION = ".sokolevel";
	
	private final ViewFacade view;
	private final ModelFacade model;
	private final PlayViewObserver playViewObserver;
	private final InitialViewObserver initialViewObserver;
	private final CraftViewObserver craftViewObserver;

	public ControllerFacadeImpl() {
		this.model = new ModelFacadeImpl();
		this.initialViewObserver = new InitialViewObserver(this, this.model);
		this.view = new ViewFacadeImpl(this);
		this.craftViewObserver = new CraftViewObserver(this.view, this.model);		
		this.playViewObserver = new PlayViewObserver(this, this.view, this.model);
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
	public LevelSequence createLevelSequence(String name, List<String> paths) throws LevelNotValidException, IOException, ClassNotFoundException {		
		return initialViewObserver.createLevelSequence(name, paths);
	}

	@Override
	public void saveLevelSequence(String path, String name, List<String> levels) throws LevelNotValidException, ClassNotFoundException, IOException {
		initialViewObserver.saveLevelSequence(path, name, levels);
	}

	@Override
	public LevelSequence loadLevelSequence(String path) throws IOException, ClassNotFoundException {
		return initialViewObserver.loadLevelSequence(path);
	}

	@Override
	public void playLevelSequence(LevelSequence levelSequence) {
		initialViewObserver.playLevelSequence(levelSequence);
	}
	
	@Override
	public void playLevel(LevelSchema levelSchema) {
		playViewObserver.playLevel(levelSchema);
	}

	@Override
	public void saveLevel(String path, LevelSchema schema) throws LevelNotValidException, FileNotFoundException, IOException {
		craftViewObserver.saveLevel(path, schema);
	}

	@Override
	public LevelSchema loadLevel(String path) throws LevelNotValidException, ClassNotFoundException, FileNotFoundException, IOException {
		return craftViewObserver.loadLevel(path);
	}
	
	@Override
	public void moveUp() {
		playViewObserver.moveUp();
	}
	
	@Override
	public void moveDown() {
		playViewObserver.moveDown();
	}
	
	@Override
	public void moveLeft() {
		playViewObserver.moveLeft();
	}
	
	@Override
	public void moveRight() {
		playViewObserver.moveRight();
	}

	@Override
	public void levelFinishedAccepted() {
		playViewObserver.levelFinishedAccepted();
	}
	
	@Override
	public void gameFinishedAccepted() {
		playViewObserver.gameFinishedAccepted();
	}
}
