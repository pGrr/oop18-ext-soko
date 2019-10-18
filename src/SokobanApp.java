import controller.ControllerFacade;
import controller.ControllerFacadeSingleton;

public class SokobanApp {

	public static void main(String[] args) {
		ControllerFacade c = ControllerFacadeSingleton.getInstance();
		c.start();
	}

}
