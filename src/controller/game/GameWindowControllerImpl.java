package controller.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import controller.Controller;
import controller.Controllers;
import controller.navigation.NavigationController;
import controller.sequence.LevelSequenceController;
import model.LevelSequence;
import model.LevelSequenceImpl;
import model.level.Level;
import model.level.grid.MovementDirection;

/**
 * The default implementation of the {@link GameWindowController} interface.
 * Implements the Singleton design pattern.
 */
public final class GameWindowControllerImpl implements GameWindowController {

    private final NavigationController navigationController;
    private final LevelSequenceController levelSequenceController;

    /**
     * Creates a new instance of game window controller using the given navigation
     * controller and level sequence controller.
     * 
     * @param navigationController    .
     * @param levelSequenceController .
     */
    public GameWindowControllerImpl(final NavigationController navigationController,
            final LevelSequenceController levelSequenceController) {
        this.navigationController = navigationController;
        this.levelSequenceController = levelSequenceController;
    }

    @Override
    public void restartCurrentLevel() {
        this.levelSequenceController.getCurrentLevelSequenceCurrentState().restartCurrentLevel();
        this.navigationController.toGameLevelView(Controller.getInstance().getLevelSequenceController()
                .getCurrentLevelSequenceCurrentState().getCurrentLevel());
    }

    @Override
    public void saveGame(final String path) throws IOException {
        LevelSequence levelSequence = this.levelSequenceController.getCurrentLevelSequenceCurrentState();
        List<Level> levels = new ArrayList<>();
        levels.add(levelSequence.getCurrentLevel());

        int currentLevelIndex = levelSequence.getAllLevels().indexOf(levelSequence.getCurrentLevel());
        currentLevelIndex += 1;
        IntStream.range(currentLevelIndex, levelSequence.getAllLevels().size())
                .mapToObj(i -> levelSequence.getAllLevels().get(i)).map(o -> (Level) o).forEachOrdered(levels::add);
        LevelSequence newLs = new LevelSequenceImpl(levelSequence.getName(), levels);
        Controllers.saveLevelSequence(newLs,
                path + Controllers.getLevelSequenceFileExtension());
    }

    @Override
    public void move(final MovementDirection direction) {
        this.levelSequenceController.getCurrentLevelSequenceCurrentState().getCurrentLevel().getUser().move(direction);
        checkLevelFinished();
    }

    @Override
    public void levelFinishedAccepted() {
        this.levelSequenceController.getCurrentLevelSequenceCurrentState().setNextLevelAsCurrent();
        this.navigationController.toGameLevelView(Controller.getInstance().getLevelSequenceController()
                .getCurrentLevelSequenceCurrentState().getCurrentLevel());
    }

    @Override
    public void gameFinishedAccepted() {
        this.navigationController.toInitialView();
    }

    /**
     * This is called after every movement. If the level is finished and there is at
     * least one next in the sequence it shows the level finished dialog. If the
     * last level in the sequence is finished it shows the game finished dialog.
     */
    private void checkLevelFinished() {
        if (this.levelSequenceController.getCurrentLevelSequenceCurrentState().getCurrentLevel().isFinished()) {
            if (this.levelSequenceController.getCurrentLevelSequenceCurrentState().hasNextLevel()) {
                this.navigationController.getGameWindow().showLevelFinishedDialog();
            } else {
                this.navigationController.getGameWindow().showGameFinishedDialog();
            }
        }
    }

    @Override
    public void backToInitialView() {
        this.levelSequenceController
                .setCurrentLevelSequence(this.levelSequenceController.getCurrentLevelSequenceInitialState());
        this.navigationController.toInitialView();
    }

    @Override
    public Level getCurrentLevel() {
        return this.levelSequenceController.getCurrentLevelSequenceCurrentState().getCurrentLevel();
    }
}
