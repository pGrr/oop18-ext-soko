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
 * The Class containing the main method to start the "Extendible-Sokoban"
 * application.
 */
public final class SokobanApp {

    private SokobanApp() {
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {
        // Initialization
        Model model = new ModelImpl();
        View view = new ViewImpl();
        Controller controller = new ControllerImpl(model, view);
        view.setController(controller);
        // if possible, it loads the default level sequence and updates it in the
        // initial view
        Optional<LevelSequence> defaultLevelSequence = controller.loadDefaultLevelSequence();
        if (defaultLevelSequence.isPresent()) {
            model.setCurrentLevelSequence(defaultLevelSequence.get());
            view.getInitialWindow().updateList(model.getLevelNames());
        }
        // shows the initial view
        view.getInitialWindow().show();
    }
}
