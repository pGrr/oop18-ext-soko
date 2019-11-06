package controller.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import controller.Controllers;
import model.Model;
import model.levelsequence.LevelSequence;
import model.levelsequence.LevelSequenceImpl;
import model.levelsequence.level.Level;
import model.levelsequence.level.grid.MovementDirection;
import view.View;

/**
 * The default implementation of the {@link GameWindowController} interface.
 * Implements the Singleton design pattern.
 */
public final class GameWindowControllerImpl implements GameWindowController {

    private final View view;
    private final Model model;

    /**
     * Creates a new instance of game window controller using the given navigation
     * controller and level sequence controller.
     * 
     * @param navigationController .
     * @param model                .
     */
    public GameWindowControllerImpl(final View navigationController, final Model model) {
        this.view = navigationController;
        this.model = model;
    }

    @Override
    public void restartCurrentLevel() {
        this.model.getCurrentLevelSequenceCurrentState().restartCurrentLevel();
        this.view.toGameLevelView(this.model.getCurrentLevelSequenceCurrentState().getCurrentLevel());
    }

    @Override
    public void saveGame(final String path) throws IOException {
        LevelSequence levelSequence = this.model.getCurrentLevelSequenceCurrentState();
        List<Level> levels = new ArrayList<>();
        levels.add(levelSequence.getCurrentLevel());

        int currentLevelIndex = levelSequence.getAllLevels().indexOf(levelSequence.getCurrentLevel());
        currentLevelIndex += 1;
        IntStream.range(currentLevelIndex, levelSequence.getAllLevels().size())
                .mapToObj(i -> levelSequence.getAllLevels().get(i)).map(o -> (Level) o).forEachOrdered(levels::add);
        LevelSequence newLs = new LevelSequenceImpl(levelSequence.getName(), levels);
        Controllers.saveLevelSequence(newLs, path + Controllers.getLevelSequenceFileExtension());
    }

    @Override
    public void move(final MovementDirection direction) {
        this.model.getCurrentLevelSequenceCurrentState().getCurrentLevel().getUser().move(direction);
        this.view.getGameWindow().draw(this.model.getCurrentLevelSequenceCurrentState().getCurrentLevel().getUser());
        checkLevelFinished();
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

    /**
     * This is called after every movement. If the level is finished and there is at
     * least one next in the sequence it shows the level finished dialog. If the
     * last level in the sequence is finished it shows the game finished dialog.
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

    @Override
    public void backToInitialView() {
        this.model.setCurrentLevelSequence(this.model.getCurrentLevelSequenceInitialState());
        this.view.toInitialView();
    }

    @Override
    public Level getCurrentLevel() {
        return this.model.getCurrentLevelSequenceCurrentState().getCurrentLevel();
    }
}
