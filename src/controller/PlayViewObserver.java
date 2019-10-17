package controller;

import java.util.List;

import model.ModelFacade;
import model.element.Element;
import model.level.LevelInstance;
import model.level.LevelSchema;
import view.ViewFacade;

public class PlayViewObserver {
	
	private final ControllerFacade owner;
	private final ViewFacade view;
	private final ModelFacade model;

	public PlayViewObserver(ControllerFacade owner, ViewFacade view, ModelFacade model) {
		this.owner = owner;
		this.view = view;
		this.model = model;
	}

	public void playLevel(LevelSchema levelSchema) {
		this.view.showPlayLevelView(levelSchema.getName());
		LevelInstance level = this.model.startLevel(levelSchema, this.view.getPlayableAreaWidth(), this.view.getPlayableAreaHeight());
		this.view.initializePlayView(level.getElements());
	}
	
	public void moveUp() {
		List<Element> updatedElements = this.model.moveUserUp();
		this.view.showElements(updatedElements);
		this.view.showBoxesOnTargets(this.model.getBoxesOnTargets());
		checkLevelFinished();
	}
	
	public void moveDown() {
		List<Element> updatedElements = this.model.moveUserDown();
		this.view.showElements(updatedElements);
		this.view.showBoxesOnTargets(this.model.getBoxesOnTargets());
		checkLevelFinished();
	}
	
	public void moveLeft() {
		List<Element> updatedElements = this.model.moveUserLeft();
		this.view.showElements(updatedElements);
		this.view.showBoxesOnTargets(this.model.getBoxesOnTargets());
		checkLevelFinished();
	}
	
	public void moveRight() {
		List<Element> updatedElements = this.model.moveUserRight();
		this.view.showElements(updatedElements);
		this.view.showBoxesOnTargets(this.model.getBoxesOnTargets());
		checkLevelFinished();
	}
	
	public void levelFinishedAccepted() {
		this.playLevel(this.model.getNextSchema());
	}
	
	public void gameFinishedAccepted() {
		this.owner.backToInitialView();
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
