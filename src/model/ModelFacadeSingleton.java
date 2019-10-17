package model;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import model.element.Element;
import model.level.LevelInstance;
import model.level.LevelInstanceImpl;
import model.level.LevelSchema;
import model.sequence.LevelSequence;

public class ModelFacadeSingleton implements ModelFacade {
	
	private final static ModelFacade SINGLETON = new ModelFacadeSingleton();
	
	private Optional<Iterator<LevelSchema>> iterator;
	private Optional<LevelInstance> currentLevel;
	
	private ModelFacadeSingleton() {
		this.currentLevel = Optional.empty();
	}
	
	public static final ModelFacade getInstance() {
		return SINGLETON;
	}
	
	@Override
	public LevelInstance startLevel(LevelSchema levelSchema, int width, int height) {
		LevelInstance levelInstance = new LevelInstanceImpl(levelSchema, width, height);
		this.currentLevel = Optional.of(levelInstance);
		return levelInstance;
	}

	@Override
	public List<Element> moveUserUp() {
		return this.currentLevel.get().moveUserUp();
	}

	@Override
	public List<Element> moveUserDown() {
		return this.currentLevel.get().moveUserDown();
	}

	@Override
	public List<Element> moveUserLeft() {
		return this.currentLevel.get().moveUserLeft();		
	}

	@Override
	public List<Element> moveUserRight() {
		return this.currentLevel.get().moveUserRight();		
	}

	@Override
	public List<Element> getBoxesOnTargets() {
		return this.currentLevel.get().getBoxesOnTarget();
	}

	@Override
	public List<Element> getAllElements() {
		return this.currentLevel.get().getElements();
	}

	@Override
	public boolean isLevelFinished() {
		return this.currentLevel.get().isFinished();
	}

	@Override
	public void startLevelSequence(LevelSequence levelSequence) {
		Objects.requireNonNull(levelSequence);
		this.iterator = Optional.of(levelSequence.iterator());
	}

	@Override
	public boolean hasNextSchema() {
		if (!iterator.isPresent()) {
			throw new IllegalStateException();
		}
		return iterator.get().hasNext();
	}

	@Override
	public LevelSchema getNextSchema() {
		if (!iterator.isPresent() || !iterator.get().hasNext()) {
			throw new IllegalStateException();
		}
		return iterator.get().next();
	}

	@Override
	public boolean isGameFinished() {
		return !this.hasNextSchema();
	}

}
