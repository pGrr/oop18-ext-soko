package controller.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import controller.Controller;
import model.Model;
import model.levelsequence.LevelSequence;
import model.levelsequence.LevelSequenceImpl;
import model.levelsequence.level.Level;
import model.levelsequence.level.grid.MovementDirection;
import view.View;

/**
 * An implementation for the {@link GameWindowController} interface.
 */
public final class GameWindowControllerImpl implements GameWindowController {

    private final Controller owner;
    private final View view;
    private final Model model;

    /**
     * Creates a new instance using the given controller, view and model.
     * 
     * @param owner the controller, creator of this object
     * @param view  the view to be controlled
     * @param model the model
     */
    public GameWindowControllerImpl(final Controller owner, final View view, final Model model) {
        this.owner = owner;
        this.view = view;
        this.model = model;
    }

    @Override
    public void move(final MovementDirection direction) {
        // moves the user in the given direction
        this.model.getCurrentLevelSequenceCurrentState().getCurrentLevel().getUser().move(direction);
        // tells the view to update the user
        this.view.getGameWindow().draw(this.model.getCurrentLevelSequenceCurrentState().getCurrentLevel().getUser());
        // checks if the level is finished
        checkLevelFinished();
    }

    @Override
    public Level getCurrentLevel() {
        return this.model.getCurrentLevelSequenceCurrentState().getCurrentLevel();
    }

    @Override
    public void restartCurrentLevel() {
        // restart the level in the model
        this.model.getCurrentLevelSequenceCurrentState().restartCurrentLevel();
        // restart the level in the view
        this.view.toGameLevelView(this.model.getCurrentLevelSequenceCurrentState().getCurrentLevel());
    }

    @Override
    public void levelFinishedAccepted() {
        this.model.getCurrentLevelSequenceCurrentState().setNextLevelAsCurrent();
        this.view.toGameLevelView(this.model.getCurrentLevelSequenceCurrentState().getCurrentLevel());
    }

    @Override
    public void gameFinishedAccepted() {
        this.view.toInitialView();
    }

    @Override
    public void saveGame(final String path) throws IOException {
        // create a level list
        List<Level> levels = new ArrayList<>();
        // add the current level
        LevelSequence levelSequence = this.model.getCurrentLevelSequenceCurrentState();
        levels.add(levelSequence.getCurrentLevel());
        // add all remaining levels
        int currentLevelIndex = levelSequence.getAllLevels().indexOf(levelSequence.getCurrentLevel());
        currentLevelIndex += 1;
        IntStream.range(currentLevelIndex, levelSequence.getAllLevels().size())
                .mapToObj(i -> levelSequence.getAllLevels().get(i)).map(o -> (Level) o).forEachOrdered(levels::add);
        // creates a new level sequence using the list
        LevelSequence newLs = new LevelSequenceImpl(levelSequence.getName(), levels);
        // saves the level sequence to the given path
        this.owner.saveLevelSequence(newLs, path + Controller.LEVEL_SEQUENCE_FILE_EXTENSION);
    }

    @Override
    public void backToInitialView() {
        // reset the current level sequence to its initial state
        this.model.setCurrentLevelSequence(this.model.getCurrentLevelSequenceInitialState());
        // goes back to initial view
        this.view.toInitialView();
    }

    /**
     * If the level is finished and there is at least one next in the sequence it
     * shows the level finished dialog. Else if the level is finished and there is
     * no other left it shows the game finished dialog.
     */
    private void checkLevelFinished() {
        if (this.model.getCurrentLevelSequenceCurrentState().getCurrentLevel().isFinished()) {
            if (this.model.getCurrentLevelSequenceCurrentState().hasNextLevel()) {
                this.view.getGameWindow().showLevelFinishedDialog();
            } else {
                this.view.getGameWindow().showGameFinishedDialog();
            }
        }
    }
}
