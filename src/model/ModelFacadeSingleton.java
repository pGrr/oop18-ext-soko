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
	
	private static ModelFacade SINGLETON;
	
	private Optional<Iterator<LevelSchema>> iterator;
	private Optional<LevelSchema> currentLevelSchema;
	private Optional<LevelInstance> currentLevelInstance;
	
	private ModelFacadeSingleton() {
		this.currentLevelInstance = Optional.empty();
	}
	
	public static final ModelFacade getInstance() {
		if (SINGLETON == null) {
			SINGLETON = new ModelFacadeSingleton();
		}
		return SINGLETON;
	}
	
	@Override
	public LevelInstance initializeLevel(LevelSchema levelSchema, int width, int height) {
		this.currentLevelSchema = Optional.of(levelSchema);
		LevelInstance levelInstance = new LevelInstanceImpl(levelSchema, width, height);
		this.currentLevelInstance = Optional.of(levelInstance);
		return levelInstance;
	}

	@Override
	public List<Element> moveUserUp() {
		return this.currentLevelInstance.get().moveUserUp();
	}

	@Override
	public List<Element> moveUserDown() {
		return this.currentLevelInstance.get().moveUserDown();
	}

	@Override
	public List<Element> moveUserLeft() {
		return this.currentLevelInstance.get().moveUserLeft();		
	}

	@Override
	public List<Element> moveUserRight() {
		return this.currentLevelInstance.get().moveUserRight();		
	}

	@Override
	public List<Element> getBoxesOnTargets() {
		return this.currentLevelInstance.get().getBoxesOnTarget();
	}

	@Override
	public List<Element> getAllElements() {
		return this.currentLevelInstance.get().getElements();
	}

	@Override
	public boolean isLevelFinished() {
		return this.currentLevelInstance.get().isFinished();
	}

	@Override
	public void initializeLevelSequence(LevelSequence levelSequence) {
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
	public LevelSchema getCurrentSchema() {
		if (this.currentLevelSchema.isPresent()) {
			return this.currentLevelSchema.get();			
		} else {
			throw new IllegalStateException();
		}
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
