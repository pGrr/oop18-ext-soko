package controller;

import static controller.ControllerConstants.LEVEL_FILE_DESCRIPTION;
import static controller.ControllerConstants.LEVEL_FILE_EXTENSION;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import model.Level;
import model.LevelNotValidException;

// TODO: Auto-generated Javadoc
/**
 * The Class LevelControllerImpl.
 */
public class LevelControllerImpl implements LevelController {

    /** The singleton. */
    private static LevelController SINGLETON;

    /**
     * Instantiates a new level controller impl.
     */
    private LevelControllerImpl() {
    }

    /**
     * Gets the single instance of LevelControllerImpl.
     *
     * @return single instance of LevelControllerImpl
     */
    public static final LevelController getInstance() {
        if (SINGLETON == null) {
            SINGLETON = new LevelControllerImpl();
        }
        return SINGLETON;
    }

    /**
     * Gets the level file extension.
     *
     * @return the level file extension
     */
    @Override
    public String getLevelFileExtension() {
        return LEVEL_FILE_EXTENSION;
    }

    /**
     * Gets the level file description.
     *
     * @return the level file description
     */
    @Override
    public String getLevelFileDescription() {
        return LEVEL_FILE_DESCRIPTION;
    }

    /**
     * Load level.
     *
     * @param path the path
     * @return the level
     * @throws LevelNotValidException the level not valid exception
     * @throws IOException            Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException the class not found exception
     */
    @Override
    public Level loadLevel(String path) throws LevelNotValidException, IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(path)))) {
            Level level = (Level) inputStream.readObject();
            level.validate();
            return level;
        }
    }

    /**
     * Save level.
     *
     * @param path  the path
     * @param level the level
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void saveLevel(String path, Level level) throws IOException {
        try (ObjectOutputStream o = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(path)))) {
            o.writeObject(level);
        }
    }
}
