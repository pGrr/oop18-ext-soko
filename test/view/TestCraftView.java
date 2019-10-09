package view;

import controller.SokobanControllerImpl;

public class TestCraftView {

	private TestCraftView() {}
	
	public static void main(String... args) {
		CraftView craftView = new CraftViewImpl(new SokobanControllerImpl());
		craftView.show();
	}

}
