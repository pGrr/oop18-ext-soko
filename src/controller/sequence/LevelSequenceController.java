package controller.sequence;

import java.util.List;

import model.LevelSequence;

/**
 * The level sequence controller, which is responsible for the main operations
 * concerning level sequences.
 */
public interface LevelSequenceController {

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
}
