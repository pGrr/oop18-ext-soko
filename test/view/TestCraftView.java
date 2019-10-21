package view;

import view.craft.CraftWindow;
import view.craft.CraftWindowImpl;

public class TestCraftView {

	private TestCraftView() {}
	
	public static void main(String... args) {
		CraftWindow craftView = new CraftWindowImpl();
		craftView.show();
	}

}
