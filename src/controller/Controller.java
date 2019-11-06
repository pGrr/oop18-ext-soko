package controller;

import controller.initial.InitialWindowController;
import model.levelsequence.LevelSequence;
import model.levelsequence.level.Level;
import model.levelsequence.level.LevelNotValidException;

import java.io.IOException;
import java.util.Optional;

import controller.craft.CraftWindowController;
import controller.game.GameWindowController;

/**
 * The main controller interface, it is the entry point to get any controller
 * function. It is responsible to start the application and to get a reference
 * to more specific controllers.
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
    String DEFAULT_LEVEL_SEQUENCE = "default.sokolevelsequence";

    /**
     * Loads a level from the given path in the file-system and, if successful, it
     * returns it.
     *
     * @param path the path of the file to be loaded
     * @return the level read from the file-system
     * @throws LevelNotValidException an application-specific exception thrown when
     *                                a level is not valid
     * @throws ClassNotFoundException the class not found exception
     * @throws IOException            Signals that an I/O exception has occurred.
     */
    Level loadLevel(String path) throws LevelNotValidException, IOException, ClassNotFoundException;

    /**
     * Saves the given level to the given path in the file-system.
     *
     * @param path  the absolute path to which save the file
     * @param level the level to be saved
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void saveLevel(String path, Level level) throws IOException;

    /**
     * Saves the given level sequence to the given path in the file-system.
     *
     * @param levelSequence the level sequence
     * @param path          the absolute path to which save the file
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void saveLevelSequence(LevelSequence levelSequence, String path) throws IOException;

    /**
     * Loads a level sequence from the given path in the file-system and, if
     * successful, it returns it.
     * 
     * @param path the absolute path of the file to be loaded in the file-system
     * @return the loaded level sequence
     * @throws IOException            Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException the class not found exception
     */
    LevelSequence loadLevelSequence(String path) throws IOException, ClassNotFoundException;

    /**
     * Tries to load from the file-system the default level sequence.
     *
     * @return the optional of the default level sequence if the loading is
     *         successful, else returns an empty optional
     */
    Optional<LevelSequence> loadDefaultLevelSequence();

    /**
     * Gets the initial window controller.
     *
     * @return the initial window controller
     * @throws IllegalStateException if the model and view have not been set for
     *                               this controller prior to this call
     */
    InitialWindowController getInitialWindowController() throws IllegalStateException;

    /**
     * Gets the craft window controller.
     *
     * @return the game controller
     * @throws IllegalStateException if the model and view have not been set for
     *                               this controller prior to this call
     */
    CraftWindowController getCraftWindowController() throws IllegalStateException;

    /**
     * Gets the game window controller.
     *
     * @return the game window controller
     * @throws IllegalStateException if the model and view have not been set for
     *                               this controller prior to this call
     */
    GameWindowController getGameWindowController() throws IllegalStateException;
}
