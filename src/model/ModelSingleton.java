package model;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class ModelSingleton.
 */
public class ModelSingleton implements Model {

    /** The singleton. */
    private static Model SINGLETON;

    /** The level sequence. */
    private LevelSequence levelSequence;

    /**
     * Instantiates a new model singleton.
     */
    private ModelSingleton() {
        this.levelSequence = new LevelSequenceImpl("");
    }

    /**
     * Gets the current level sequence.
     *
     * @return the current level sequence
     */
    @Override
    public LevelSequence getCurrentLevelSequence() {
        if (levelSequence != null) {
            return levelSequence;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Sets the current level sequence.
     *
     * @param levelSequence the new current level sequence
     */
    @Override
    public void setCurrentLevelSequence(LevelSequence levelSequence) {
        this.levelSequence = levelSequence;
    }

    /**
     * Gets the single instance of ModelSingleton.
     *
     * @return single instance of ModelSingleton
     */
    public static Model getInstance() {
        if (SINGLETON == null) {
            SINGLETON = new ModelSingleton();
        }
        return SINGLETON;
    }

}
