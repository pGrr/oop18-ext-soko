package view;

import controller.SokobanControllerImpl;
import view.play.PlayViewContainer;

public class TestPlayView {

	private TestPlayView() {}
	
	public static void main(String ...args) {
		new PlayViewContainer(new SokobanControllerImpl(), "").show();
	}

}
