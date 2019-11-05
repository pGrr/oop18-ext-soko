package controller;

import java.io.IOException;
import controller.initial.InitialWindowController;
import controller.craft.CraftWindowController;
import controller.game.GameWindowController;
import controller.navigation.NavigationController;
import controller.sequence.LevelSequenceController;
import model.LevelSequence;
import model.level.Level;
import model.level.LevelNotValidException;

/**
 * The main controller interface, it is the entry point to get any controller
 * function. It is responsible to start the application and to get a reference
 * to more specific controllers.
 */
public interface Controller {

    /**
     * Gets the navigation controller, which is responsible for showing and hiding
     * Windows.
     *
     * @return the navigation controller
     */
    NavigationController getNavigationController();

    /**
     * Gets the sequence controller, which is responsible for the main operations
     * concerning level sequences.
     *
     * @return the sequence controller
     */
    LevelSequenceController getLevelSequenceController();

    /**
     * Gets the initial window controller.
     *
     * @return the initial window controller
     */
    InitialWindowController getInitialWindowObserver();

    /**
     * Gets the craft window controller.
     *
     * @return the game controller
     */
    CraftWindowController getCraftWindowController();

    /**
     * Gets the game window controller.
     *
     * @return the game window controller
     */
    GameWindowController getGameWindowController();

    /**
     * Gets the level file description.
     *
     * @return the level file description
     */
    String getLevelFileDescription();

    /**
     * Gets the level file extension.
     *
     * @return the level file extension
     */
    String getLevelFileExtension();

    /**
     * Gets the level sequence file description.
     *
     * @return the level sequence file description
     */
    String getLevelSequenceFileDescription();

    /**
     * Gets the level sequence file extension.
     *
     * @return the level sequence file extension
     */
    String getLevelSequenceFileExtension();

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
    Level loadLevel(String path) throws LevelNotValidException, ClassNotFoundException, IOException;

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
     * Gets the single instance of Controller.
     *
     * @return single instance of Controller
     */
    static Controller getInstance() {
        return ControllerSingleton.getInstance();
    }
}
