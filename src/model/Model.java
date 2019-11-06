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
     * @param levelSequence the new current level sequence
     */
    void setCurrentLevelSequence(LevelSequence levelSequence);

    /**
     * Returns the names of the levels of the current level sequence.
     * 
     * @return the level names list
     */
    List<String> getLevelNames();

    /**
     * Gets the current level sequence in its current state.
     *
     * @return the current level sequence
     */
    LevelSequence getCurrentLevelSequenceCurrentState();

    /**
     * Gets the current level sequence in its initial state.
     *
     * @return the current level sequence in its initial state
     */
    LevelSequence getCurrentLevelSequenceInitialState();
}
