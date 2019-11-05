package controller;

import java.util.Optional;
import java.util.stream.Collectors;
import controller.craft.CraftWindowController;
import controller.craft.CraftWindowControllerImpl;
import controller.game.GameWindowController;
import controller.game.GameWindowControllerImpl;
import controller.initial.InitialWindowController;
import controller.initial.InitialWindowControllerImpl;
import controller.navigation.NavigationController;
import controller.navigation.NavigationControllerImpl;
import controller.sequence.LevelSequenceController;
import controller.sequence.LevelSequenceControllerImpl;
import model.LevelSequence;
import model.level.Level;
import view.craft.CraftWindow;
import view.craft.CraftWindowImpl;
import view.game.GameWindow;
import view.game.GameWindowImpl;
import view.initial.InitialWindow;
import view.initial.InitialWindowImpl;

/**
 * The implementation class for the {@link Controller} interface, it implements
 * the Singleton design pattern.
 */
public final class ControllerSingleton implements Controller {

    private static Controller singleton;
    private final NavigationController navigationController;
    private final LevelSequenceController levelSequenceController;
    private final InitialWindowController initialWindowController;
    private final CraftWindowController craftWindowController;
    private final GameWindowController gameWindowController;

    /**
     * Initializes the controller singleton object.
     */
    private ControllerSingleton() {
        InitialWindow initialWindow = new InitialWindowImpl();
        CraftWindow craftWindow = new CraftWindowImpl();
        GameWindow gameWindow = new GameWindowImpl();
        this.navigationController = new NavigationControllerImpl(initialWindow, craftWindow, gameWindow);
        this.levelSequenceController = new LevelSequenceControllerImpl();
        this.loadDefaultLevelSequence();
        this.gameWindowController = new GameWindowControllerImpl(this.navigationController,
                this.levelSequenceController);
        this.craftWindowController = new CraftWindowControllerImpl(craftWindow);
        this.initialWindowController = new InitialWindowControllerImpl(this, initialWindow);
        initialWindow.setController(initialWindowController);
        craftWindow.setController(craftWindowController);
        gameWindow.setController(gameWindowController);
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
    public NavigationController getNavigationController() {
        return this.navigationController;
    }

    @Override
    public LevelSequenceController getLevelSequenceController() {
        return this.levelSequenceController;
    }

    @Override
    public InitialWindowController getInitialWindowObserver() {
        return this.initialWindowController;
    }

    @Override
    public CraftWindowController getCraftWindowController() {
        return this.craftWindowController;
    }

    @Override
    public GameWindowController getGameWindowController() {
        return this.gameWindowController;
    }

    /**
     * Tries to load from the file-system the default level sequence. If successful,
     * it sets it as the model and updates the level list in initial window
     *
     * @return the optional of the default level sequence if the loading is
     *         successful, else returns an empty optional
     */
    private void loadDefaultLevelSequence() {
        Optional<LevelSequence> ls = Controllers.loadDefaultLevelSequence();
        if (ls.isPresent()) {
            getLevelSequenceController().setCurrentLevelSequence(ls.get());
            getNavigationController().getInitialWindow()
                    .updateList(getLevelSequenceController().getCurrentLevelSequenceCurrentState().getAllLevels()
                            .stream().map(Level::getName).collect(Collectors.toList()));
        }
    }
}
