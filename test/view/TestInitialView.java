package view;

import view.initial.InitialViewWindow;
import view.initial.InitialViewWindowImpl;

public class TestInitialView {

	private TestInitialView() {}

	public static void main(String ...args) {
		InitialViewWindow initialView = new InitialViewWindowImpl();
		initialView.show();
	}
}
