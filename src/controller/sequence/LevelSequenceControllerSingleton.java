package controller.sequence;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import model.Model;
import model.sequence.LevelSequence;
import view.View;
import controller.Controller;

import static controller.ControllerConstants.LEVEL_SEQUENCE_FILE_EXTENSION;
import static controller.ControllerConstants.LEVEL_SEQUENCE_FILE_DESCRIPTION;

/**
 * The default implementation of the {@link LevelSequenceController} interface.
 * Implements the singleton design pattern.
 */
public final class LevelSequenceControllerSingleton implements LevelSequenceController {

    private static LevelSequenceController singleton;

    private LevelSequenceControllerSingleton() {
    }

    /**
     * Gets the single instance of LevelSequenceControllerImpl.
     *
     * @return single instance of LevelSequenceControllerImpl
     */
    public static LevelSequenceController getInstance() {
        if (singleton == null) {
            singleton = new LevelSequenceControllerSingleton();
        }
        return singleton;
    }

    @Override
    public String getLevelSequenceFileExtension() {
        return LEVEL_SEQUENCE_FILE_EXTENSION;
    }

    @Override
    public String getLevelSequenceFileDescription() {
        return LEVEL_SEQUENCE_FILE_DESCRIPTION;
    }

    @Override
    public void saveLevelSequence(final LevelSequence levelSequence, final String path) throws IOException {
        try (ObjectOutputStream o = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(new File(path))))) {
            o.writeObject(levelSequence);
        }
    }

    @Override
    public LevelSequence loadLevelSequence(final String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream o = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(new File(path))))) {
            return (LevelSequence) o.readObject();
        }
    }

    @Override
    public void startLevelSequence(final LevelSequence levelSequence) {
        Model.getInstance().setCurrentLevelSequence(levelSequence);
        if (Model.getInstance().getCurrentState().hasNextLevel()) {
            Model.getInstance().getCurrentState().setNextLevelAsCurrent();
            Controller.getInstance().getNavigationController()
                    .toGameLevelView(Model.getInstance().getCurrentState().getCurrentLevel());
        } else {
            View.getInstance().getInitialWindow().showLevelSequenceEmptyErrorDialog();
        }
    }
}
