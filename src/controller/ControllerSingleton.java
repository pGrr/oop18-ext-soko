package controller;

import java.util.Optional;

import controller.game.GameController;
import model.LevelSequence;
import model.Model;
import view.View;

/**
 * The implementation class for the Controller interface, it implements the
 * Singleton design pattern.
 */
public final class ControllerSingleton implements Controller {

    private static Controller singleton;
    private final NavigationController navigationController;
    private final LevelController levelController;
    private final LevelSequenceController levelSequenceController;
    private final GameController gameController;

    private ControllerSingleton() {
        this.navigationController = NavigationController.getDefaultInstance();
        this.levelController = LevelController.getDefaultInstance();
        this.levelSequenceController = LevelSequenceController.getDefaultInstance();
        this.gameController = GameController.getDefaultInstance();
    }

    /**
     * Starts the application.
     */
    @Override
    public void startApplication() {
        View.getInstance().getInitialWindow().show();
        Optional<LevelSequence> ls = LevelSequence.createDefault();
        if (ls.isPresent()) {
            Model.getInstance().setCurrentLevelSequence(ls.get());
            View.getInstance().getInitialWindow().updateListModel();
        }
    }

    /**
     * Gets the single instance of ControllerSingleton.
     *
     * @return single instance of ControllerSingleton
     */
    public static Controller getInstance() {
        if (singleton == null) {
            singleton = new ControllerSingleton();
        }
        return singleton;
    }

    /**
     * Gets the navigation controller, which is responsible for showing and hiding
     * Windows.
     *
     * @return the navigation controller
     */
    @Override
    public NavigationController getNavigationController() {
        return this.navigationController;
    }

    /**
     * Gets the level controller, which is responsible for the main operations
     * concerning levels.
     *
     * @return the level controller
     */
    @Override
    public LevelController getLevelController() {
        return this.levelController;
    }

    /**
     * Gets the sequence controller, which is responsible for the main operations
     * concerning level sequences.
     *
     * @return the sequence controller
     */
    @Override
    public LevelSequenceController getSequenceController() {
        return this.levelSequenceController;
    }

    /**
     * Gets the game controller, which is responsible for the operations necessary
     * when playing a level.
     *
     * @return the game controller
     */
    @Override
    public GameController getGameController() {
        return this.gameController;
    }
}
