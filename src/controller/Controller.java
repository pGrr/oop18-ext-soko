package controller;

import java.io.IOException;
import java.util.Optional;
import controller.initial.InitialWindowController;
import model.levelsequence.LevelSequence;
import model.levelsequence.level.Level;
import model.levelsequence.level.LevelNotValidException;
import controller.craft.CraftWindowController;
import controller.game.GameWindowController;

/**
 * The main controller interface, it is the entry point to get any specific
 * controller.
 */
public interface Controller {

    /** The level sequence file description. */
    String LEVEL_SEQUENCE_FILE_DESCRIPTION = "Sokoban level-sequence files (*.sokolevelsequence)";

    /** The level sequence file extension. */
    String LEVEL_SEQUENCE_FILE_EXTENSION = ".sokolevelsequence";

    /** The level file description. */
    String LEVEL_FILE_DESCRIPTION = "Sokoban level files (*.sokolevel)";

    /** The level file extension. */
    String LEVEL_FILE_EXTENSION = ".sokolevel";

    /** The default level sequence file name. */
    String DEFAULT_LEVEL_SEQUENCE = "defaultlevelsequence/default.sokolevelsequence";

    /**
     * Loads a level from the given path in the file-system and returns it.
     *
     * @param path the path of the level in the file-system
     * @return the level read from the file-system
     * @throws LevelNotValidException if the loaded level is not valid
     * @throws ClassNotFoundException if the loaded object is not recognized (e.g.
     *                                level is corrupted)
     * @throws IOException            if an I/O exception has occurred.
     */
    Level loadLevel(String path) throws LevelNotValidException, IOException, ClassNotFoundException;

    /**
     * Saves the given level to the given path in the file-system.
     *
     * @param path  the absolute path to which save the file
     * @param level the level to be saved
     * @throws IOException if an I/O exception has occurred.
     */
    void saveLevel(String path, Level level) throws IOException;

    /**
     * Loads a level sequence from the given path in the file-system and returns it.
     * 
     * @param path the absolute path of the file to be loaded in the file-system
     * @return the loaded level sequence
     * @throws IOException            if an I/O exception has occurred.
     * @throws ClassNotFoundException if the loaded object is not recognized (e.g.
     *                                level is corrupted)
     */
    LevelSequence loadLevelSequence(String path) throws IOException, ClassNotFoundException;

    /**
     * Saves the given level sequence to the given path in the file-system.
     *
     * @param levelSequence the level sequence to be saved
     * @param path          the absolute path to which save the level sequence
     * @throws IOException            if an I/O exception has occurred.
     */
    void saveLevelSequence(LevelSequence levelSequence, String path) throws IOException;

    /**
     * Attempts the loading of the default level sequence.
     *
     * @return an optional containing the default level sequence if the loading is
     *         successful, else an empty optional
     */
    Optional<LevelSequence> loadDefaultLevelSequence();

    /**
     * Gets the {@link InitialWindow} controller.
     *
     * @return the initial window controller
     * @throws IllegalStateException if the model and view have not been set for
     *                               this controller prior to this call
     */
    InitialWindowController getInitialWindowController() throws IllegalStateException;

    /**
     * Gets the {@link CraftWindow} controller.
     *
     * @return the craft window controller
     * @throws IllegalStateException if the model and view have not been set for
     *                               this controller prior to this call
     */
    CraftWindowController getCraftWindowController() throws IllegalStateException;

    /**
     * Gets the {@link GameWindow} controller.
     *
     * @return the game window controller
     * @throws IllegalStateException if the model and view have not been set for
     *                               this controller prior to this call
     */
    GameWindowController getGameWindowController() throws IllegalStateException;
}
