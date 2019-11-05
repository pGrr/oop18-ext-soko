package controller.sequence;

import model.LevelSequence;
import model.LevelSequenceImpl;
import model.level.Level;

import java.util.List;
import java.util.stream.Collectors;

import controller.Controller;

/**
 * The default implementation of the {@link LevelSequenceController} interface.
 * Implements the singleton design pattern.
 */
public final class LevelSequenceControllerImpl implements LevelSequenceController {

    private LevelSequence lsCurrentState;
    private LevelSequence lsInitialState;

    /**
     * Initializes a new level sequence controller with the given level sequence.
     */
    public LevelSequenceControllerImpl() {
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
        if (lsCurrentState != null) {
            return lsCurrentState;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public LevelSequence getCurrentLevelSequenceInitialState() {
        if (lsInitialState != null) {
            return lsInitialState;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void startLevelSequence(final LevelSequence levelSequence) {
        setCurrentLevelSequence(levelSequence);
        if (getCurrentLevelSequenceCurrentState().hasNextLevel()) {
            getCurrentLevelSequenceCurrentState().setNextLevelAsCurrent();
            Controller.getInstance().getNavigationController()
                    .toGameLevelView(getCurrentLevelSequenceCurrentState().getCurrentLevel());
        } else {
            Controller.getInstance().getNavigationController().getInitialWindow().showLevelSequenceEmptyErrorDialog();
        }
    }
}
