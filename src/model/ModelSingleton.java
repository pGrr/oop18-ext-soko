package model;

import java.util.ArrayList;

import model.sequence.LevelSequence;
import model.sequence.LevelSequenceImpl;
import model.sequence.LevelSequences;

// TODO: Auto-generated Javadoc
/**
 * The Class ModelSingleton.
 */
public class ModelSingleton implements Model {

    /** The singleton. */
    private static Model SINGLETON;

    /** The level sequence. */
    private LevelSequence levelSequenceCurrentState;
    
    private LevelSequence levelSequenceInitialState;

    /**
     * Instantiates a new model singleton.
     */
    private ModelSingleton() {
        this.levelSequenceCurrentState = new LevelSequenceImpl("");
        this.levelSequenceInitialState = LevelSequences.createCopyOf(levelSequenceCurrentState);
    }

    /**
     * Gets the current level sequence.
     *
     * @return the current level sequence
     */
    @Override
    public LevelSequence getCurrentLevelSequence() {
        if (levelSequenceCurrentState != null) {
            return levelSequenceCurrentState;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public LevelSequence getCurrentLevelSequenceInitialState() {
        if (levelSequenceInitialState != null) {
            return levelSequenceInitialState;
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
        this.levelSequenceCurrentState = levelSequence;
        this.levelSequenceInitialState = LevelSequences.createCopyOf(levelSequenceCurrentState);
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
