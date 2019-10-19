package controller;

import java.util.List;
import model.ModelFacade;
import model.element.Element;
import view.ViewFacade;

public class PlayViewObserver {
	
	private static PlayViewObserver SINGLETON;
	
	private final ViewFacade view;
	private final ModelFacade model;

	public PlayViewObserver() {
		this.view = ViewFacade.getInstance();
		this.model = ModelFacade.getInstance();
	}
	
	public static final PlayViewObserver getInstance() {
		if (SINGLETON == null) {
			SINGLETON = new PlayViewObserver();
		}
		return SINGLETON;
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
		ControllerFacade.getInstance().playLevel(this.model.getNextSchema());
	}
	
	public void gameFinishedAccepted() {
		ControllerFacade.getInstance().backToInitialView();
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
