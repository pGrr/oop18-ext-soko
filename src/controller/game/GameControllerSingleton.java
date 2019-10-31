package controller.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import controller.Controller;
import model.Direction;
import model.Model;
import model.level.Level;
import model.sequence.LevelSequence;
import model.sequence.LevelSequenceImpl;
import view.View;

/**
 * The default implementation of the {@link GameController} interface.
 * Implements the Singleton design pattern.
 */
public final class GameControllerSingleton implements GameController {

    private static GameController singleton;

    private GameControllerSingleton() {
    }

    /**
     * Gets the single instance of GameControllerImpl.
     *
     * @return single instance of GameControllerImpl
     */
    public static GameController getInstance() {
        if (singleton == null) {
            singleton = new GameControllerSingleton();
        }
        return singleton;
    }

    @Override
    public void restartCurrentLevel() {
        Model.getInstance().getCurrentLevelSequence().restartCurrentLevel();
        Controller.getInstance().getNavigationController()
                .toGameLevelView(Model.getInstance().getCurrentLevelSequence().getCurrentLevel());
    }

    @Override
    public void saveGame(final String path) throws IOException {
        LevelSequence levelSequence = Model.getInstance().getCurrentLevelSequence();
        List<Level> levels = new ArrayList<>();
        levels.add(levelSequence.getCurrentLevel());

        int currentLevelIndex = levelSequence.getAllLevels().indexOf(levelSequence.getCurrentLevel());
        currentLevelIndex += 1;
        IntStream.range(currentLevelIndex, levelSequence.getAllLevels().size())
                .mapToObj(i -> levelSequence.getAllLevels().get(i)).map(o -> (Level) o).forEachOrdered(levels::add);
        LevelSequence newLs = new LevelSequenceImpl(levelSequence.getName(), levels);
        Controller.getInstance().getLevelSequenceController().saveLevelSequence(newLs,
                path + Controller.getInstance().getLevelSequenceController().getLevelSequenceFileExtension());
    }

    @Override
    public void move(final Direction direction) {
        Model.getInstance().getCurrentLevelSequence().getCurrentLevel().getUser().move(direction);
        checkLevelFinished();
    }

    @Override
    public void levelFinishedAccepted() {
        Model.getInstance().getCurrentLevelSequence().setNextLevelAsCurrent();
        Controller.getInstance().getNavigationController()
                .toGameLevelView(Model.getInstance().getCurrentLevelSequence().getCurrentLevel());
    }

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
