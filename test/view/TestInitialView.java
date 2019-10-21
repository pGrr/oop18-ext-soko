package view;

import view.initial.InitialWindow;
import view.initial.InitialWindowImpl;

public class TestInitialView {

	private TestInitialView() {}

	public static void main(String ...args) {
		InitialWindow initialView = new InitialWindowImpl();
		initialView.show();
	}
}
