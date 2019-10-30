package controller.levelsequence;

import java.io.IOException;

import model.LevelSequence;

// TODO: Auto-generated Javadoc
/**
 * The Interface LevelSequenceController.
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
     * Save level sequence.
     *
     * @param levelSequence the level sequence
     * @param path          the path
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void saveLevelSequence(LevelSequence levelSequence, String path) throws IOException;

    /**
     * Load level sequence.
     *
     * @param path the path
     * @return the level sequence
     * @throws IOException            Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException the class not found exception
     */
    LevelSequence loadLevelSequence(String path) throws IOException, ClassNotFoundException;

    /**
     * Start level sequence.
     *
     * @param levelSequence the level sequence
     */
    void startLevelSequence(LevelSequence levelSequence);

    /**
     * Gets the default instance.
     *
     * @return the default instance
     */
    static LevelSequenceController getDefaultInstance() {
        return LevelSequenceControllerImpl.getInstance();
    }

}
