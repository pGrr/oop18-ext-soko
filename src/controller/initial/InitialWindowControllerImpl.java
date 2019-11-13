package controller.initial;

import java.io.IOException;
import controller.Controller;
import model.Model;
import model.levelsequence.LevelSequence;
import model.levelsequence.LevelSequenceImpl;
import model.levelsequence.level.LevelNotValidException;
import view.initial.InitialWindow;

/**
 * An implementation of {@link InitialWindowController}.
 */
public final class InitialWindowControllerImpl implements InitialWindowController {

    private final Controller owner;
    private final Model model;
    private final InitialWindow view;

    /**
     * Initializes a new instance with the given controller, model and view.
     * 
     * @param owner         the controller, creator of this object
     * @param initialWindow the view to be controlled
     * @param model         the model
     */
    public InitialWindowControllerImpl(final Controller owner, final Model model, final InitialWindow initialWindow) {
        this.owner = owner;
        this.view = initialWindow;
        this.model = model;
    }

    @Override
    public void addLevel(final String path) throws ClassNotFoundException, LevelNotValidException, IOException {
        this.model.getCurrentLevelSequenceCurrentState().add(this.owner.loadLevel(path));
        updateLevelList();
    }

    @Override
    public void swap(final int i, final int j) {
        this.model.getCurrentLevelSequenceCurrentState().swap(i, j);
        updateLevelList();
    }

    @Override
    public void remove(final int i) {
        this.model.getCurrentLevelSequenceCurrentState().remove(i);
        updateLevelList();
    }

    @Override
    public void removeAll() {
        this.model.getCurrentLevelSequenceCurrentState().clear();
        updateLevelList();
    }

    @Override
    public void toCraftLevelView() {
        this.view.toCraftLevelView();
    }

    @Override
    public void play() {
        if (this.model.getCurrentLevelSequenceCurrentState().hasNextLevel()) {
            this.model.getCurrentLevelSequenceCurrentState().setNextLevelAsCurrent();
            this.view.toGameView(this.model.getCurrentLevelSequenceCurrentState().getCurrentLevel());
        } else {
            this.view.showLevelSequenceEmptyErrorDialog();
        }
    }

    @Override
    public void updateLevelList() {
        this.view.updateList(this.model.getLevelNames());
    }

    @Override
    public void saveLevelSequence(final String name, final String path) throws IOException {
        LevelSequence ls = this.model.getCurrentLevelSequenceCurrentState();
        if (ls.getAllLevels().isEmpty()) {
            this.view.showLevelSequenceEmptyErrorDialog();
        } else {
            this.owner.saveLevelSequence(
                    new LevelSequenceImpl(name, this.model.getCurrentLevelSequenceCurrentState().getAllLevels()), path);
        }
    }

    @Override
    public void loadLevelSequence(final String path) throws ClassNotFoundException, IOException {
        LevelSequence currentLs = this.model.getCurrentLevelSequenceCurrentState();
        LevelSequence loadedLs = this.owner.loadLevelSequence(path);
        if (currentLs.getAllLevels().isEmpty()) {
            // if the current level sequence is empty it is substituted with the loaded one
            this.model.setCurrentLevelSequence(loadedLs);
        } else {
            // if current level sequence is not empty levels are added to it
            loadedLs.getAllLevels().stream().forEach(currentLs::add);
            this.model.setCurrentLevelSequence(currentLs);
        }
        updateLevelList();
    }
}
