package controller;

public class TestController {

	private TestController() {}

	public static void main(String ...args) {
		ControllerFacade c = new ControllerFacadeImpl();
		c.start();
	}		
}
