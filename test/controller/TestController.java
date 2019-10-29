package controller;

// TODO: Auto-generated Javadoc
/**
 * The Class TestController.
 */
public class TestController {

    /**
     * Instantiates a new test controller.
     */
    private TestController() {
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String... args) {
        Controller c = ControllerSingleton.getInstance();
        c.startApplication();
    }
}
