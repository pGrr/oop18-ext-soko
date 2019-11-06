package controller.initial;

import java.io.IOException;
import controller.Controllers;
import model.Model;
import model.levelsequence.LevelSequenceImpl;
import model.levelsequence.level.LevelNotValidException;
import view.initial.InitialWindow;

/**
 * An implementation class for the {@link InitialWindowController} interface.
 */
public final class InitialWindowControllerImpl implements InitialWindowController {

    private final Model model;
    private final InitialWindow view;

    /**
     * Initializes a new initial view controller for the given initial window.
     * 
     * @param initialWindow the initial window
     * @param model         the model
     */
    public InitialWindowControllerImpl(final Model model, final InitialWindow initialWindow) {
        this.view = initialWindow;
        this.model = model;
    }

    @Override
    public void addLevel(final String path) throws ClassNotFoundException, LevelNotValidException, IOException {
        this.model.getCurrentLevelSequenceCurrentState().add(Controllers.loadLevel(path));
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
    public void saveLevelSequence(final String name, final String path) throws IOException {
        Controllers.saveLevelSequence(
                new LevelSequenceImpl(name, this.model.getCurrentLevelSequenceCurrentState().getAllLevels()), path);
    }

    @Override
    public void loadLevelSequence(final String path) throws ClassNotFoundException, IOException {
        this.model.setCurrentLevelSequence(Controllers.loadLevelSequence(path));
        updateLevelList();
    }

    @Override
    public void updateLevelList() {
        this.view.updateList(this.model.getLevelNames());
    }
}
