package controller.initial;

import java.io.IOException;
import controller.Controller;
import model.LevelSequenceImpl;
import model.level.LevelNotValidException;
import view.initial.InitialWindow;

/**
 * An implementation class for the {@link InitialWindowController} interface.
 */
public final class InitialWindowControllerImpl implements InitialWindowController {

    private final Controller owner;
    private final InitialWindow initialWindow;

    /**
     * Initializes a new initial view controller for the given initial window.
     * 
     * @param owner         the Controller object which creates and contains this
     *                      object
     * @param initialWindow the initial window
     */
    public InitialWindowControllerImpl(final Controller owner, final InitialWindow initialWindow) {
        this.owner = owner;
        this.initialWindow = initialWindow;
    }

    @Override
    public void addLevel(final String path) throws ClassNotFoundException, LevelNotValidException, IOException {
        this.owner.getLevelSequenceController().getCurrentLevelSequenceCurrentState().add(this.owner.loadLevel(path));
        updateLevelList();
    }

    @Override
    public void swap(final int i, final int j) {
        this.owner.getLevelSequenceController().getCurrentLevelSequenceCurrentState().swap(i, j);
        updateLevelList();
    }

    @Override
    public void remove(final int i) {
        this.owner.getLevelSequenceController().getCurrentLevelSequenceCurrentState().remove(i);
        updateLevelList();
    }

    @Override
    public void removeAll() {
        this.owner.getLevelSequenceController().getCurrentLevelSequenceCurrentState().clear();
        updateLevelList();
    }

    @Override
    public void toCraftLevelView() {
        this.owner.getNavigationController().toCraftLevelView();
    }

    @Override
    public void play() {
        this.owner.getLevelSequenceController()
                .startLevelSequence(this.owner.getLevelSequenceController().getCurrentLevelSequenceCurrentState());
    }

    @Override
    public void saveLevelSequence(final String name, final String path) throws IOException {
        this.owner.saveLevelSequence(
                new LevelSequenceImpl(name,
                        this.owner.getLevelSequenceController().getCurrentLevelSequenceCurrentState().getAllLevels()),
                path);
    }

    @Override
    public void loadLevelSequence(final String path) throws ClassNotFoundException, IOException {
        this.owner.getLevelSequenceController().setCurrentLevelSequence(this.owner.loadLevelSequence(path));
        updateLevelList();
    }

    @Override
    public void updateLevelList() {
        this.initialWindow.updateList(this.owner.getLevelSequenceController().getLevelNames());
    }
}
