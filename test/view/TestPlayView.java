package view;

import controller.ControllerFacadeSingleton;
import view.play.PlayViewContainer;

public class TestPlayView {

	private TestPlayView() {}
	
	public static void main(String ...args) {
		new PlayViewContainer(new ControllerFacadeSingleton(), "").show();
	}

}
