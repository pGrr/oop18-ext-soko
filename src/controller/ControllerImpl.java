package controller;

import controller.craft.CraftWindowController;
import controller.craft.CraftWindowControllerImpl;
import controller.game.GameWindowController;
import controller.game.GameWindowControllerImpl;
import controller.initial.InitialWindowController;
import controller.initial.InitialWindowControllerImpl;
import model.Model;
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
     * Instantiates a new controller with the given model and view.
     *
     * @param model the model
     * @param view the view
     */
    public ControllerImpl(final Model model, final View view) {
        this.model = model;
        this.view = view;
        this.gameWindowController = new GameWindowControllerImpl(this.view, this.model);
        this.craftWindowController = new CraftWindowControllerImpl(this.model, this.view.getCraftWindow());
        this.initialWindowController = new InitialWindowControllerImpl(this.model, this.view.getInitialWindow());
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
     * Throws an illegal state exception if the model and view have not been set for this
     * controller prior to this call.
     * 
     * @throws IllegalStateException if the model and view have not been set for this
     *                               controller prior to this call
     */
    private void mustBeSet() throws IllegalStateException {
        if (this.model == null || this.view == null) {
            throw new IllegalStateException("Model and view has not been set");
        }
    }
}
