package model;

import java.util.List;

import controller.SokobanController;
import model.Element.Type;
import model.LevelImpl.LevelNotValidException;

public class SokobanModelImpl implements SokobanModel {
	
	@SuppressWarnings("unused")
	private final SokobanController controller;
	private LevelSequence levelSequence;
	
	public SokobanModelImpl(SokobanController controller) {
		this.controller = controller;
		this.levelSequence = new LevelSequenceImpl();
	}
	
	@Override
	public LevelSequence getLevelSequence() {
		return this.levelSequence;
	}
	
	@Override
	public Level convertFromTypeGrid(String name, List<List<Type>> typeGrid) throws LevelNotValidException {
		return new LevelImpl(typeGrid, name);
	}

	@Override
	public List<List<Type>> convertToTypeGrid(Level level) {
		return level.getTypeGrid();
	}
	
}
