package view;

import controller.ControllerFacadeImpl;
import view.craft.CraftView;
import view.craft.CraftViewContainer;

public class TestCraftView {

	private TestCraftView() {}
	
	public static void main(String... args) {
		CraftView craftView = new CraftViewContainer(new ControllerFacadeImpl());
		craftView.show();
	}

}
