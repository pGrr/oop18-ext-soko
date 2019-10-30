package controller.level;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import model.Level;
import model.LevelNotValidException;

import static controller.ControllerConstants.LEVEL_FILE_DESCRIPTION;
import static controller.ControllerConstants.LEVEL_FILE_EXTENSION;

/**
 * The default implementation of the {@link LevelController} interface.
 * Implements the singleton design pattern.
 */
public final class LevelControllerSingleton implements LevelController {

    private static LevelController singleton;

    private LevelControllerSingleton() {
    }

    /**
     * Gets the single instance of LevelControllerImpl.
     *
     * @return single instance of LevelControllerImpl
     */
    public static LevelController getInstance() {
        if (singleton == null) {
            singleton = new LevelControllerSingleton();
        }
        return singleton;
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
}
