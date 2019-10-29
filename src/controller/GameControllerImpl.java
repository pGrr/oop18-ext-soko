package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Direction;
import model.Level;
import model.LevelSequence;
import model.Model;
import view.View;

/**
 * The default implementation of the {@link GameController} interface.
 */
public final class GameControllerImpl implements GameController {

    private static GameController singleton;

    private GameControllerImpl() {
    }

    /**
     * Gets the single instance of GameControllerImpl.
     *
     * @return single instance of GameControllerImpl
     */
    public static GameController getInstance() {
        if (singleton == null) {
            singleton = new GameControllerImpl();
        }
        return singleton;
    }

    /**
     * Restarts the current level. Every movement performed since the beginning of
     * the level will be lost and every element will return to the original
     * position. This is called from the view when the user triggers the
     * corresponding event.
     */
    @Override
    public void restartCurrentLevel() {
        Controller.getInstance().getNavigationController()
                .toGameLevel(Model.getInstance().getCurrentLevelSequence().getCurrentLevel());
    }

    /**
     * Saves the current game. More specifically, it creates a new level sequence
     * with the current level in its current state as a first level and all the
     * ordered remaining levels next to it.
     *
     * @param path the path
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void saveGame(final String path) throws IOException {
        List<Level> levels = new ArrayList<>();
        LevelSequence ls = Model.getInstance().getCurrentLevelSequence();
        levels.add(ls.getCurrentLevel());
        while (ls.hasNextLevel()) {
            ls.setNextLevel();
            levels.add(ls.getCurrentLevel());
        }
        Controller.getInstance().getSequenceController()
                .saveLevelSequence(LevelSequence.createFromLevels(ls.getName(), levels), path);
    }

    /**
     * Performs the action of the user trying to move in a specified direction. It
     * is called from the view when the player triggers the movement event and it
     * updates the model subsequently upon the game logics (the movement can result
     * in a change of element positions or not).
     *
     * @param direction the direction
     */
    @Override
    public void move(final Direction direction) {
        Model.getInstance().getCurrentLevelSequence().getCurrentLevel().getUser().move(direction);
        checkLevelFinished();
    }

    /**
     * This is called when the user accepts the level finished message which is
     * called when a level is finished and there is another one next. This function
     * starts the next level.
     */
    @Override
    public void levelFinishedAccepted() {
        Model.getInstance().getCurrentLevelSequence().setNextLevel();
        Controller.getInstance().getNavigationController()
                .toGameLevel(Model.getInstance().getCurrentLevelSequence().getCurrentLevel());
    }

    /**
     * This is called when the user accepts the game finished message, wich is
     * called when the last level of a sequence is finished. This function go back
     * to the initial view.
     */
    @Override
    public void gameFinishedAccepted() {
        Controller.getInstance().getNavigationController().toInitialView();
    }

    /*
     * This is called after every movement. If the level is finished and there is at
     * least one next in the sequence it shows the level finished dialog. If the
     * last level in the sequence is finished it shows the game finished dialog.
     */
    private void checkLevelFinished() {
        if (Model.getInstance().getCurrentLevelSequence().getCurrentLevel().isFinished()) {
            if (Model.getInstance().getCurrentLevelSequence().hasNextLevel()) {
                View.getInstance().getGameWindow().showLevelFinishedDialog();
            } else {
                View.getInstance().getGameWindow().showGameFinishedDialog();
            }
        }
    }
}
