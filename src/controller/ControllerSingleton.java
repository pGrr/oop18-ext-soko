package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import model.level.LevelNotValidException;
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

    private static final String LEVEL_SEQUENCE_FILE_DESCRIPTION = "Sokoban level-sequence files (*.sokolevelsequence)";
    private static final String LEVEL_SEQUENCE_FILE_EXTENSION = ".sokolevelsequence";
    private static final String LEVEL_FILE_DESCRIPTION = "Sokoban level files (*.sokolevel)";
    private static final String LEVEL_FILE_EXTENSION = ".sokolevel";
    private static final String DEFAULT_LEVEL_SEQUENCE = "default.sokolevelsequence";

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

    @Override
    public String getLevelFileDescription() {
        return LEVEL_FILE_DESCRIPTION;
    }

    @Override
    public String getLevelFileExtension() {
        return LEVEL_FILE_EXTENSION;
    }

    @Override
    public String getLevelSequenceFileExtension() {
        return LEVEL_SEQUENCE_FILE_EXTENSION;
    }

    @Override
    public String getLevelSequenceFileDescription() {
        return LEVEL_SEQUENCE_FILE_DESCRIPTION;
    }

    @Override
    public Level loadLevel(final String path) throws LevelNotValidException, IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(path)))) {
            Level level = (Level) inputStream.readObject();
            level.validate();
            return level;
        }
    }

    @Override
    public void saveLevel(final String path, final Level level) throws IOException {
        try (ObjectOutputStream o = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(path)))) {
            o.writeObject(level);
        }
    }

    @Override
    public void saveLevelSequence(final LevelSequence levelSequence, final String path) throws IOException {
        try (ObjectOutputStream o = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(new File(path))))) {
            o.writeObject(levelSequence);
        }
    }

    @Override
    public LevelSequence loadLevelSequence(final String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream o = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(new File(path))))) {
            return (LevelSequence) o.readObject();
        }
    }

    /**
     * Tries to load from the file-system the default level sequence.
     *
     * @return the optional of the default level sequence if the loading is
     *         successful, else returns an empty optional
     */
    private void loadDefaultLevelSequence() {
        try {
            Optional<LevelSequence> ls = Optional
                    .of(loadLevelSequence(ClassLoader.getSystemResource(DEFAULT_LEVEL_SEQUENCE).getPath()));
            if (ls.isPresent()) {
                getLevelSequenceController().setCurrentLevelSequence(ls.get());
                getNavigationController().getInitialWindow()
                        .updateList(getLevelSequenceController().getCurrentLevelSequenceCurrentState().getAllLevels()
                                .stream().map(Level::getName).collect(Collectors.toList()));
            }
        } catch (Exception e) {
            // if the default sequence can't be loaded, there will just be an initial empty
            // level sequence
        }
    }
}
