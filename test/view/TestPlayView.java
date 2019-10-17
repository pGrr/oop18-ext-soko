package view;

import controller.ControllerFacadeImpl;
import view.play.PlayViewContainer;

public class TestPlayView {

	private TestPlayView() {}
	
	public static void main(String ...args) {
		new PlayViewContainer(new ControllerFacadeImpl(), "").show();
	}

}
