package model;

import java.util.List;
import java.util.stream.Collectors;
import model.levelsequence.LevelSequence;
import model.levelsequence.LevelSequenceImpl;
import model.levelsequence.level.Level;

/**
 * An implementation of {@link Model}.
 */
public final class ModelImpl implements Model {

    private LevelSequence lsCurrentState;
    private LevelSequence lsInitialState;

    /**
     * Creates a new instance containing an empty level sequence.
     */
    public ModelImpl() {
        this.lsCurrentState = new LevelSequenceImpl("");
        this.lsInitialState = this.lsCurrentState.createCopy();
    }

    @Override
    public void setCurrentLevelSequence(final LevelSequence levelSequence) {
        this.lsCurrentState = levelSequence;
        this.lsInitialState = this.lsCurrentState.createCopy();
    }

    @Override
    public List<String> getLevelNames() {
        return this.lsCurrentState.getAllLevels().stream().map(Level::getName).collect(Collectors.toList());
    }

    @Override
    public LevelSequence getCurrentLevelSequenceCurrentState() {
        if (this.lsCurrentState != null) {
            return this.lsCurrentState;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public LevelSequence getCurrentLevelSequenceInitialState() {
        if (this.lsInitialState != null) {
            return this.lsInitialState;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
