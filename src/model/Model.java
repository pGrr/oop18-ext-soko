package model;

// TODO: Auto-generated Javadoc
/**
 * The Interface Model.
 */
public interface Model {

    /**
     * Gets the current level sequence.
     *
     * @return the current level sequence
     */
    LevelSequence getCurrentLevelSequence();

    /**
     * Sets the current level sequence.
     *
     * @param levelSequence the new current level sequence
     */
    void setCurrentLevelSequence(LevelSequence levelSequence);

    /**
     * Gets the single instance of Model.
     *
     * @return single instance of Model
     */
    static Model getInstance() {
        return ModelSingleton.getInstance();
    }

    LevelSequence getCurrentLevelSequenceInitialState();

}
