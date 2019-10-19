package view;

import view.craft.CraftViewWindow;
import view.craft.CraftViewWindowImpl;

public class TestCraftView {

	private TestCraftView() {}
	
	public static void main(String... args) {
		CraftViewWindow craftView = new CraftViewWindowImpl();
		craftView.show();
	}

}
