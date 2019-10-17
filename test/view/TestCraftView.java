package view;

import controller.ControllerFacadeSingleton;
import view.craft.CraftView;
import view.craft.CraftViewContainer;

public class TestCraftView {

	private TestCraftView() {}
	
	public static void main(String... args) {
		CraftView craftView = new CraftViewContainer(new ControllerFacadeSingleton());
		craftView.show();
	}

}
