package controller;

public class TestController {

	private TestController() {}

	public static void main(String ...args) {
		ControllerFacade c = ControllerFacadeImpl.getInstance();
		c.start();
	}		
}
