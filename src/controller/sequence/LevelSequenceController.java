package controller.sequence;

import java.io.IOException;

import model.sequence.LevelSequence;

/**
 * The level sequence controller, which is responsible for the main operations
 * concerning level sequences.
 */
public interface LevelSequenceController {

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
     * Starts a given level sequence. In more detail: 1) it sets the given level
     * sequence as the current level sequence in the model and it starts the
     * iteration setting the first level as the current level. 3) It commands the
     * view to show the game window for that level. If the sequence does not contain
     * any level, it commands the view to warn the user with an an error dialog.
     *
     * @param levelSequence the level sequence to be started
     */
    void startLevelSequence(LevelSequence levelSequence);

    /**
     * Gets the default level sequence object.
     *
     * @return the single object of the default implementing class, which is
     *         {@link LevelSequenceControllerSingleton}
     */
    static LevelSequenceController getDefaultInstance() {
        return LevelSequenceControllerSingleton.getInstance();
    }
}
