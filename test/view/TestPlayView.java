package view;

import controller.SokobanControllerImpl;

public class TestPlayView {

	private TestPlayView() {}
	
	public static void main(String ...args) {
		new PlayViewImpl(new SokobanControllerImpl()).show();
	}

}
