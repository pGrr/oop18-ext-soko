package controller.level;

import java.io.IOException;
import model.Level;
import model.LevelNotValidException;

/**
 * The level controller, which is responsible for the operations concerning
 * levels.
 */
public interface LevelController {

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
     * Gets the default level controller object.
     *
     * @return the single instance of the default implementing class, which is
     *         {@link LevelControllerSingleton}
     */
    static LevelController getDefaultInstance() {
        return LevelControllerSingleton.getInstance();
    }
}
