package model;

import java.util.List;
import java.util.Optional;

public class SokobanModelImpl implements SokobanModel {
	
	private Optional<LevelInstance> currentLevel;
	
	public SokobanModelImpl() {
		this.currentLevel = Optional.empty();
	}
	
	@Override
	public void startLevel(LevelInstance levelInstance) {
		this.currentLevel = Optional.of(levelInstance);
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

}
