package view;

import java.util.List;
import java.util.Optional;
import controller.ControllerFacade;
import model.element.Element;
import view.craft.CraftViewWindow;
import view.craft.CraftViewWindowImpl;
import view.initial.InitialViewWindow;
import view.initial.InitialViewWindowImpl;
import view.play.PlayViewWindow;
import view.play.PlayViewWindowImpl;

public class ViewFacadeSingleton implements ViewFacade {
	
	private static final String LEVEL_NOT_INITIALIZED_ERROR_TEXT = "Level has not been initialized.";
	private static ViewFacade SINGLETON;

	private final InitialViewWindow initialView;
	private final CraftViewWindow craftView;
	private Optional<PlayViewWindow> playView;
	
	private ViewFacadeSingleton() {
		this.craftView = new CraftViewWindowImpl();
		this.playView = Optional.empty();
		this.initialView = new InitialViewWindowImpl();
	}

	public static final ViewFacade getInstance() {
		if (SINGLETON == null) {
			SINGLETON = new ViewFacadeSingleton();
		}
		return SINGLETON;
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
		this.playView = Optional.of(new PlayViewWindowImpl(ControllerFacade.getInstance(), name));
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
		}	
	}
}
