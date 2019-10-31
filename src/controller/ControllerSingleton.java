package controller;

import java.util.Optional;
import controller.game.GameController;
import controller.level.LevelController;
import controller.navigation.NavigationController;
import controller.sequence.LevelSequenceController;
import model.Model;
import model.sequence.LevelSequence;
import model.sequence.LevelSequences;
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

    @Override
    public void startApplication() {
        View.getInstance().getInitialWindow().show();
        Optional<LevelSequence> ls = LevelSequences.createDefault();
        if (ls.isPresent()) {
            Model.getInstance().setCurrentLevelSequence(ls.get());
            View.getInstance().getInitialWindow().updateListModel();
        }
    }

    @Override
    public NavigationController getNavigationController() {
        return this.navigationController;
    }

    @Override
    public LevelController getLevelController() {
        return this.levelController;
    }

    @Override
    public LevelSequenceController getLevelSequenceController() {
        return this.levelSequenceController;
    }

    @Override
    public GameController getGameController() {
        return this.gameController;
    }
}
