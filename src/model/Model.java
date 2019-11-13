package model;

import java.util.List;

import model.levelsequence.LevelSequence;

/**
 * The model, which is responsible for managing the level sequences and all of
 * its components.
 */
public interface Model {

    /**
     * Sets the current level sequence.
     *
     * @param levelSequence the new level sequence
     */
    void setCurrentLevelSequence(LevelSequence levelSequence);

    /**
     * Returns the names of the levels.
     * 
     * @return the level names list
     */
    List<String> getLevelNames();

    /**
     * Gets the level sequence in its current state.
     * 
     * @return the level sequence in its current state
     */
    LevelSequence getCurrentLevelSequenceCurrentState();

    /**
     * Gets the level sequence in its initial state.
     *
     * @return the level sequence in its initial state
     */
    LevelSequence getCurrentLevelSequenceInitialState();
}
