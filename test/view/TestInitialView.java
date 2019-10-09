package view;

import controller.SokobanControllerImpl;

public class TestInitialView {

	private TestInitialView() {}

	public static void main(String ...args) {
		InitialView initialView = new InitialViewImpl(new SokobanControllerImpl());
		initialView.show();
	}
}
