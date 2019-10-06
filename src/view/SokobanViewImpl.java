package view;

import java.util.List;

import controller.SokobanController;
import model.Element.Type;

public class SokobanViewImpl implements SokobanView {
	
	@SuppressWarnings("unused")
	private static final String ERROR_DIALOG_TITLE = "ERROR";
	@SuppressWarnings("unused")
	private static final String ERROR_DIALOG_LEVEL_NOT_VALID = "It seems like the level is not valid. Please check the following: 1) A singular initial point exists; 2) At least one target exists; 3) An equal number of boxes and targets exists.";

	private final InitialView initialView;
	private final CraftView craftView;
	private final PlayView playView;
	
	public SokobanViewImpl(SokobanController controller) {
		this.initialView = new InitialViewImpl(controller);
		this.craftView = new CraftViewImpl(controller);
		this.playView = new PlayViewImpl(controller);
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
	public void showPlayLevelView(String name, List<List<Type>> typeGrid) {
		this.hideAll();
		this.playView.show();
	}
	
	private void hideAll() {
		this.initialView.hide();
		this.craftView.hide();
		this.playView.hide();
	}
	
}
