package view;

import controller.ControllerFacadeSingleton;
import view.initial.InitialViewWindow;
import view.initial.InitialViewWindowImpl;

public class TestInitialView {

	private TestInitialView() {}

	public static void main(String ...args) {
		InitialViewWindow initialView = new InitialViewWindowImpl(ControllerFacadeSingleton.getInstance());
		initialView.show();
	}
}
