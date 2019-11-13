package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;
import controller.craft.CraftWindowController;
import controller.craft.CraftWindowControllerImpl;
import controller.game.GameWindowController;
import controller.game.GameWindowControllerImpl;
import controller.initial.InitialWindowController;
import controller.initial.InitialWindowControllerImpl;
import model.Model;
import model.levelsequence.LevelSequence;
import model.levelsequence.level.Level;
import model.levelsequence.level.LevelNotValidException;
import view.View;

/**
 * The implementation class for the {@link Controller} interface.
 */
public final class ControllerImpl implements Controller {

    private InitialWindowController initialWindowController;
    private CraftWindowController craftWindowController;
    private GameWindowController gameWindowController;
    private Model model;
    private View view;

    /**
     * Instantiates a new instance with the given model and view.
     *
     * @param model the model
     * @param view  the view
     */
    public ControllerImpl(final Model model, final View view) {
        this.model = model;
        this.view = view;
        this.gameWindowController = new GameWindowControllerImpl(this, this.view, this.model);
        this.craftWindowController = new CraftWindowControllerImpl(this, this.view.getCraftWindow());
        this.initialWindowController = new InitialWindowControllerImpl(this, this.model, this.view.getInitialWindow());
    }

    @Override
    public Level loadLevel(final String path) throws LevelNotValidException, IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(path)))) {
            Level level = (Level) inputStream.readObject();
            level.validate();
            return level;
        }
    }

    @Override
    public void saveLevel(final String path, final Level level) throws IOException {
        try (ObjectOutputStream o = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(path)))) {
            o.writeObject(level);
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
    public void saveLevelSequence(final LevelSequence levelSequence, final String path) throws IOException {
        try (ObjectOutputStream o = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(new File(path))))) {
            o.writeObject(levelSequence);
        }
    }

    @Override
    public Optional<LevelSequence> loadDefaultLevelSequence() {
        Optional<LevelSequence> ls = Optional.empty();
        try (ObjectInputStream o = new ObjectInputStream(
                new BufferedInputStream(ClassLoader.getSystemResourceAsStream(Controller.DEFAULT_LEVEL_SEQUENCE)))) {
            ls = Optional.of((LevelSequence) o.readObject());
        } catch (Exception e) {
            // if the default sequence can't be loaded, there will just be an initial empty
            // level sequence
        }
        return ls;
    }

    @Override
    public InitialWindowController getInitialWindowController() throws IllegalStateException {
        mustBeSet();
        return this.initialWindowController;
    }

    @Override
    public CraftWindowController getCraftWindowController() throws IllegalStateException {
        mustBeSet();
        return this.craftWindowController;
    }

    @Override
    public GameWindowController getGameWindowController() throws IllegalStateException {
        mustBeSet();
        return this.gameWindowController;
    }

    /**
     * Throws an illegal state exception if model and view have not been set for
     * this controller prior to this call.
     * 
     * @throws IllegalStateException if the model and view have not been set for
     *                               this controller prior to this call
     */
    private void mustBeSet() throws IllegalStateException {
        if (this.model == null || this.view == null) {
            throw new IllegalStateException("Model and view has not been set");
        }
    }
}
