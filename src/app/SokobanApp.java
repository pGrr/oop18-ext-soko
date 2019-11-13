package app;

import java.util.Optional;
import controller.Controller;
import controller.ControllerImpl;
import model.Model;
import model.ModelImpl;
import model.levelsequence.LevelSequence;
import view.View;
import view.ViewImpl;

/**
 * Contains the main method to start the application.
 */
public final class SokobanApp {

    private SokobanApp() {
    }

    /**
     * The main method.
     *
     * @param args the main arguments
     */
    public static void main(final String[] args) {
        // Initialize
        Model model = new ModelImpl();
        View view = new ViewImpl();
        Controller controller = new ControllerImpl(model, view);
        view.setController(controller);
        // if possible, it loads the default level sequence
        Optional<LevelSequence> defaultLevelSequence = controller.loadDefaultLevelSequence();
        if (defaultLevelSequence.isPresent()) {
            // updates it in the initial view
            model.setCurrentLevelSequence(defaultLevelSequence.get());
            view.getInitialWindow().updateList(model.getLevelNames());
        }
        // shows the initial view
        view.getInitialWindow().show();
    }
}
