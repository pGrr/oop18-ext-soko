package view;

public class TestInitialView {

	private TestInitialView() {}

	public static void main(String ...args) {
		InitialView initialView = new InitialViewImpl();
		initialView.show();
	}
}
