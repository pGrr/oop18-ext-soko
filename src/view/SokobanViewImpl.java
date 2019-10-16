package view;

import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;
import controller.SokobanController;
import model.Element;
import model.LevelSequence;
import view.craft.CraftView;
import view.craft.CraftViewContainer;
import view.initial.InitialView;
import view.initial.InitialViewContainer;
import view.play.PlayView;
import view.play.PlayViewContainer;

public class SokobanViewImpl implements SokobanView {
	
	private static final String LEVEL_NOT_INITIALIZED_ERROR_TEXT = "Level has not been initialized.";
	private static final String DEFAULT_LEVEL_SEQUENCE = "default.sokolevelsequence";

	private final SokobanController controller;
	private final InitialView initialView;
	private final CraftView craftView;
	private Optional<LevelSequence> defaultLevelSequence;
	private Optional<PlayView> playView;
	
	public SokobanViewImpl(SokobanController controller) {
		this.controller = controller;
		this.defaultLevelSequence = Optional.empty();
		try {
			String path = URLDecoder.decode(ClassLoader.getSystemResource(DEFAULT_LEVEL_SEQUENCE).getPath(), "UTF-8");
			this.defaultLevelSequence = Optional.of(this.controller.loadLevelSequence(path));
		} catch (Exception e) {
			System.err.println(e);
		}
		this.initialView = this.defaultLevelSequence.isPresent() ? 
				new InitialViewContainer(controller, this.defaultLevelSequence.get()) : new InitialViewContainer(controller);			
		this.craftView = new CraftViewContainer(controller);
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
		this.playView = Optional.of(new PlayViewContainer(this.controller, name));
		this.playView.get().show();
	}

	@Override
	public int getPlayableAreaWidth() {
		if (this.playView.isPresent()) {
			return this.playView.get().getGameAreaWidth();			
		} else {
			throw new RuntimeException(LEVEL_NOT_INITIALIZED_ERROR_TEXT);
		}
	}

	@Override
	public int getPlayableAreaHeight() {
		if (this.playView.isPresent()) {
			return this.playView.get().getGameAreaHeight();
		} else {
			throw new RuntimeException(LEVEL_NOT_INITIALIZED_ERROR_TEXT);
		}
	}

	@Override
	public void showElements(List<Element> elements) {
		if (this.playView.isPresent()) {
			this.playView.get().drawElements(elements);			
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
			this.playView.get().drawBoxesCoveringTargets(boxesCoveringTargets);
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
