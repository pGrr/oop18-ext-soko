package model;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import controller.SokobanController;
import model.Element.Type;
import model.LevelImpl.LevelNotValidException;

public class SokobanModelImpl implements SokobanModel {
	
	@SuppressWarnings("unused")
	private final SokobanController controller;
	private LevelSequence levelSequence;
	private Optional<Iterator<Level>> levelIterator;
	
	public SokobanModelImpl(SokobanController controller) {
		this.controller = controller;
		this.levelSequence = new LevelSequenceImpl();
		this.levelIterator = Optional.empty();
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

	@Override
	public void moveUserUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveUserDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveUserLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveUserRight() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setLevelSequence(LevelSequence levelSequence) {
		this.levelSequence = levelSequence;
	}
	
	@Override
	public void startLevelSequence() {
		if (this.levelIterator.isPresent()) {
			throw new RuntimeException("A level sequence has already been started and has not yet finished.");
		} else {			
			this.levelIterator = Optional.of(this.levelSequence.iterator());
		}
	}

	@Override
	public void playLevelSequence(LevelSequence levelSequence) {
		this.setLevelSequence(levelSequence);
		this.startLevelSequence();
		while (this.hasNextLevel()) {
			this.controller.playLevel(this.getNextLevel());
		}
	}
	
	@Override
	public final boolean hasNextLevel() {
		return this.getLevelIterator().hasNext();
	}
	
	@Override
	public final Level getNextLevel() {
		return this.getLevelIterator().next();
	}
	
	private final Iterator<Level> getLevelIterator() {
		if (!this.levelIterator.isPresent()) {
			throw new RuntimeException("Level iterator has not been started.");
		}
		return levelIterator.get();
	}
	
}
