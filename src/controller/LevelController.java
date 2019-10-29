package controller;

import java.io.IOException;
import model.Level;
import model.LevelNotValidException;

// TODO: Auto-generated Javadoc
/**
 * The Interface LevelController.
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
     * Load level.
     *
     * @param path the path
     * @return the level
     * @throws LevelNotValidException the level not valid exception
     * @throws ClassNotFoundException the class not found exception
     * @throws IOException            Signals that an I/O exception has occurred.
     */
    Level loadLevel(String path) throws LevelNotValidException, ClassNotFoundException, IOException;

    /**
     * Save level.
     *
     * @param path   the path
     * @param schema the schema
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void saveLevel(String path, Level schema) throws IOException;

    /**
     * Gets the default instance.
     *
     * @return the default instance
     */
    static LevelController getDefaultInstance() {
        return LevelControllerImpl.getInstance();
    }
}
