package view;

import controller.ControllerFacadeSingleton;
import view.play.PlayViewWindowImpl;

public class TestPlayView {

	private TestPlayView() {}
	
	public static void main(String ...args) {
		new PlayViewWindowImpl(ControllerFacadeSingleton.getInstance(), "").show();
	}

}
