package view;

import controller.ControllerFacadeSingleton;
import view.initial.InitialView;
import view.initial.InitialViewContainer;

public class TestInitialView {

	private TestInitialView() {}

	public static void main(String ...args) {
		InitialView initialView = new InitialViewContainer(new ControllerFacadeSingleton());
		initialView.show();
	}
}
