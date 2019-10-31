package model;

import model.sequence.LevelSequence;
import model.sequence.LevelSequenceImpl;
import model.sequence.LevelSequences;

/**
 * The implementation class for the {@link Model} interface. Implements the
 * Singleton design pattern.
 */
public final class ModelSingleton implements Model {

    private static Model singleton;

    private LevelSequence lsCurrentState;
    private LevelSequence lsInitialState;

    private ModelSingleton() {
        this.lsCurrentState = new LevelSequenceImpl("");
        this.lsInitialState = LevelSequences.createCopyOf(lsCurrentState);
    }

    @Override
    public void setCurrentLevelSequence(final LevelSequence levelSequence) {
        this.lsCurrentState = levelSequence;
        this.lsInitialState = LevelSequences.createCopyOf(lsCurrentState);
    }

    @Override
    public LevelSequence getCurrentState() {
        if (lsCurrentState != null) {
            return lsCurrentState;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public LevelSequence getInitialState() {
        if (lsInitialState != null) {
            return lsInitialState;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Gets the single instance of ModelSingleton.
     *
     * @return single instance of ModelSingleton
     */
    public static Model getInstance() {
        if (singleton == null) {
            singleton = new ModelSingleton();
        }
        return singleton;
    }
}
