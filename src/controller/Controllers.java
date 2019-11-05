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
import model.LevelSequence;
import model.level.Level;
import model.level.LevelNotValidException;

/**
 * An helper static class for all controllers.
 */
public final class Controllers {

    private static final String LEVEL_SEQUENCE_FILE_DESCRIPTION = "Sokoban level-sequence files (*.sokolevelsequence)";
    private static final String LEVEL_SEQUENCE_FILE_EXTENSION = ".sokolevelsequence";
    private static final String LEVEL_FILE_DESCRIPTION = "Sokoban level files (*.sokolevel)";
    private static final String LEVEL_FILE_EXTENSION = ".sokolevel";
    private static final String DEFAULT_LEVEL_SEQUENCE = "default.sokolevelsequence";

    /**
     * Non-instantiable.
     */
    private Controllers() {
    }

    /**
     * Gets the level file description.
     *
     * @return the level file description
     */
    public static String getLevelFileDescription() {
        return LEVEL_FILE_DESCRIPTION;
    }

    /**
     * Gets the level file extension.
     *
     * @return the level file extension
     */
    public static String getLevelFileExtension() {
        return LEVEL_FILE_EXTENSION;
    }

    /**
     * Gets the level sequence file description.
     *
     * @return the level sequence file description
     */
    public static String getLevelSequenceFileDescription() {
        return LEVEL_SEQUENCE_FILE_DESCRIPTION;
    }

    /**
     * Gets the level sequence file extension.
     *
     * @return the level sequence file extension
     */
    public static String getLevelSequenceFileExtension() {
        return LEVEL_SEQUENCE_FILE_EXTENSION;
    }

    /**
     * Gets the default level sequence file name.
     * 
     * @return the default level sequence file name
     */
    public static String getDefaultLevelSequenceName() {
        return DEFAULT_LEVEL_SEQUENCE;
    }

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
    public static Level loadLevel(final String path)
            throws LevelNotValidException, IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(path)))) {
            Level level = (Level) inputStream.readObject();
            level.validate();
            return level;
        }
    }

    /**
     * Saves the given level to the given path in the file-system.
     *
     * @param path  the absolute path to which save the file
     * @param level the level to be saved
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void saveLevel(final String path, final Level level) throws IOException {
        try (ObjectOutputStream o = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(path)))) {
            o.writeObject(level);
        }
    }

    /**
     * Saves the given level sequence to the given path in the file-system.
     *
     * @param levelSequence the level sequence
     * @param path          the absolute path to which save the file
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void saveLevelSequence(final LevelSequence levelSequence, final String path) throws IOException {
        try (ObjectOutputStream o = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(new File(path))))) {
            o.writeObject(levelSequence);
        }
    }

    /**
     * Loads a level sequence from the given path in the file-system and, if
     * successful, it returns it.
     * 
     * @param path the absolute path of the file to be loaded in the file-system
     * @return the loaded level sequence
     * @throws IOException            Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException the class not found exception
     */
    public static LevelSequence loadLevelSequence(final String path) throws IOException, ClassNotFoundException {
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
    public static Optional<LevelSequence> loadDefaultLevelSequence() {
        Optional<LevelSequence> ls = Optional.empty();
        try {
             ls = Optional.of(Controllers.loadLevelSequence(
                    ClassLoader.getSystemResource(Controllers.getDefaultLevelSequenceName()).getPath()));

        } catch (Exception e) {
            // if the default sequence can't be loaded, there will just be an initial empty
            // level sequence
        }
        return ls;
    }
}
