package controller;

public class TestController {

	private TestController() {}

	public static void main(String ...args) {
		SokobanController c = new SokobanControllerImpl();
		c.start();
	}		
}
