package view;

import java.util.List;
import java.util.Optional;
import controller.SokobanController;
import model.Element;
import view.craft.CraftView;
import view.craft.CraftViewImpl;
import view.initial.InitialView;
import view.initial.InitialViewImpl;
import view.play.PlayView;
import view.play.PlayViewImpl;

public class SokobanViewImpl implements SokobanView {
	
	private static final String LEVEL_NOT_INITIALIZED_ERROR_TEXT = "Level has not been initialized.";

	private final SokobanController controller;
	private final InitialView initialView;
	private final CraftView craftView;
	private Optional<PlayView> playView;
	
	public SokobanViewImpl(SokobanController controller) {
		this.controller = controller;
		this.initialView = new InitialViewImpl(controller);
		this.craftView = new CraftViewImpl(controller);
		this.playView = Optional.empty();
	}

	@Override
	public void showInitialView() {
		this.hideAll();
		this.initialView.show();
	}

	@Override
	public void showCraftLevelView() {
		this.hideAll();
		this.craftView.show();
	}

	@Override
	public void showPlayLevelView(String name) {
		this.hideAll();
		this.playView = Optional.of(new PlayViewImpl(this.controller, name));
		this.playView.get().show();
	}

	@Override
	public int getPlayableAreaWidth() {
		if (this.playView.isPresent()) {
			return this.playView.get().getPlayAreaWidth();			
		} else {
			throw new RuntimeException(LEVEL_NOT_INITIALIZED_ERROR_TEXT);
		}
	}

	@Override
	public int getPlayableAreaHeight() {
		if (this.playView.isPresent()) {
			return this.playView.get().getPlayAreaHeight();
		} else {
			throw new RuntimeException(LEVEL_NOT_INITIALIZED_ERROR_TEXT);
		}
	}

	@Override
	public void showElements(List<Element> elements) {
		if (this.playView.isPresent()) {
			this.playView.get().showElements(elements);			
		} else {
			throw new RuntimeException(LEVEL_NOT_INITIALIZED_ERROR_TEXT);
		}
	}

	private void hideAll() {
		this.initialView.hide();
		this.craftView.hide();
		if (this.playView.isPresent()) {
			this.playView.get().hide();			
		}
	}

	@Override
	public void showBoxesOnTargets(List<Element> boxesCoveringTargets) {
		if (this.playView.isPresent()) {			
			this.playView.get().showBoxesCoveringTargets(boxesCoveringTargets);
		} else {
			throw new IllegalStateException("Play view has not been initialized");
		}
	}

	@Override
	public void initializePlayView(List<Element> elements) {
		if (this.playView.isPresent()) {			
			this.playView.get().initialize(elements);;
		} else {
			throw new IllegalStateException("Play view has not been initialized");
		}
	}

	@Override
	public void showLevelFinishedDialog() {
		if (this.playView.isPresent()) {			
			this.playView.get().showLevelFinishedDialog();
		} else {
			throw new IllegalStateException("Play view has not been initialized");
		}
	}

	@Override
	public void showGameFinishedDialog() {
		if (this.playView.isPresent()) {			
			this.playView.get().showGameFinishedDialog();
		} else {
			throw new IllegalStateException("Play view has not been initialized");
		}	}

}
