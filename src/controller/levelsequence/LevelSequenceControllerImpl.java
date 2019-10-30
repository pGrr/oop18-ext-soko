package controller.levelsequence;

import static controller.ControllerConstants.*;
import static view.InitialConstants.*;

import view.GuiComponentFactory;
import model.Model;
import model.LevelSequence;

import java.io.*;

import controller.Controller;

// TODO: Auto-generated Javadoc
/**
 * The Class LevelSequenceControllerImpl.
 */
public class LevelSequenceControllerImpl implements LevelSequenceController {

    /** The singleton. */
    private static LevelSequenceController SINGLETON;

    /**
     * Instantiates a new level sequence controller impl.
     */
    private LevelSequenceControllerImpl() {
    }

    /**
     * Gets the single instance of LevelSequenceControllerImpl.
     *
     * @return single instance of LevelSequenceControllerImpl
     */
    public static final LevelSequenceController getInstance() {
        if (SINGLETON == null) {
            SINGLETON = new LevelSequenceControllerImpl();
        }
        return SINGLETON;
    }

    /**
     * Gets the level sequence file extension.
     *
     * @return the level sequence file extension
     */
    @Override
    public String getLevelSequenceFileExtension() {
        return LEVEL_SEQUENCE_FILE_EXTENSION;
    }

    /**
     * Gets the level sequence file description.
     *
     * @return the level sequence file description
     */
    @Override
    public String getLevelSequenceFileDescription() {
        return LEVEL_SEQUENCE_FILE_DESCRIPTION;
    }

    /**
     * Save level sequence.
     *
     * @param levelSequence the level sequence
     * @param path          the path
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void saveLevelSequence(LevelSequence levelSequence, String path) throws IOException {
        try (ObjectOutputStream o = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(new File(path))))) {
            o.writeObject(levelSequence);
        }
    }

    /**
     * Load level sequence.
     *
     * @param path the path
     * @return the level sequence
     * @throws IOException            Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException the class not found exception
     */
    @Override
    public LevelSequence loadLevelSequence(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream o = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(new File(path))))) {
            return (LevelSequence) o.readObject();
        }
    }

    /**
     * Start level sequence.
     *
     * @param levelSequence the level sequence
     */
    @Override
    public void startLevelSequence(LevelSequence levelSequence) {
        Model.getInstance().setCurrentLevelSequence(levelSequence);
        if (Model.getInstance().getCurrentLevelSequence().hasNextLevel()) {
            Model.getInstance().getCurrentLevelSequence().setNextLevel();
            Controller.getInstance().getNavigationController()
                    .toGameLevel(Model.getInstance().getCurrentLevelSequence().getCurrentLevel());
        } else {
            GuiComponentFactory.getDefaultInstance()
                    .createNotifyDialog(null, DIALOG_ERROR_TITLE, DIALOG_ERROR_LEVEL_SEQUENCE_EMPTY_TEXT)
                    .setVisible(true);
            ;
        }
    }

}
