package view;

import controller.SokobanControllerImpl;
import view.craft.CraftView;
import view.craft.CraftViewImpl;

public class TestCraftView {

	private TestCraftView() {}
	
	public static void main(String... args) {
		CraftView craftView = new CraftViewImpl(new SokobanControllerImpl());
		craftView.show();
	}

}
