package view;

import controller.SokobanControllerImpl;
import view.initial.InitialView;
import view.initial.InitialViewImpl;

public class TestInitialView {

	private TestInitialView() {}

	public static void main(String ...args) {
		InitialView initialView = new InitialViewImpl(new SokobanControllerImpl());
		initialView.show();
	}
}
