package controller;

import java.util.List;
import java.util.Optional;

import model.ModelFacade;
import model.element.Element;
import view.ViewFacade;

public class PlayViewObserver {
	
	private static Optional<PlayViewObserver> SINGLETON = Optional.empty();
	
	private final ControllerFacade owner;
	private final ViewFacade view;
	private final ModelFacade model;

	public PlayViewObserver(ControllerFacade owner, ViewFacade view, ModelFacade model) {
		this.owner = owner;
		this.view = view;
		this.model = model;
	}
	
	public static final PlayViewObserver getInstance(ControllerFacade owner, ViewFacade view, ModelFacade model) {
		if (!SINGLETON.isPresent()) {
			SINGLETON = Optional.of(new PlayViewObserver(owner, view, model));
		}
		return SINGLETON.get();
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
		this.owner.playLevel(this.model.getNextSchema());
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
