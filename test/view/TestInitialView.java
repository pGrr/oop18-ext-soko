package view;

import controller.ControllerFacadeImpl;
import view.initial.InitialView;
import view.initial.InitialViewContainer;

public class TestInitialView {

	private TestInitialView() {}

	public static void main(String ...args) {
		InitialView initialView = new InitialViewContainer(new ControllerFacadeImpl());
		initialView.show();
	}
}
