package view;

import java.util.Objects;
import controller.Controller;
import model.levelsequence.level.Level;
import view.craft.CraftWindow;
import view.craft.CraftWindowImpl;
import view.game.GameWindow;
import view.game.GameWindowImpl;
import view.initial.InitialWindow;
import view.initial.InitialWindowImpl;

/**
 * An implementation of {@link View}.
 */
public final class ViewImpl implements View {

    private final InitialWindow initialWindow;
    private final CraftWindow craftWindow;
    private final GameWindow gameWindow;
    private Controller controller;

    /**
     * Creates a new instance containing a new {@link InitialWindow}, a new
     * {@link CraftWindow} and a new {@link GameWindow}. A controller must be set
     * afterwards construction before the first use.
     */
    public ViewImpl() {
        this.initialWindow = new InitialWindowImpl(this);
        this.craftWindow = new CraftWindowImpl(this);
        this.gameWindow = new GameWindowImpl(this);
    }

    @Override
    public void setController(final Controller controller) {
        this.controller = controller;
        this.initialWindow.setController(this.controller.getInitialWindowController());
        this.craftWindow.setController(this.controller.getCraftWindowController());
        this.gameWindow.setController(this.controller.getGameWindowController());
    }

    @Override
    public InitialWindow getInitialWindow() {
        Objects.requireNonNull(this.initialWindow);
        return this.initialWindow;
    }

    @Override
    public CraftWindow getCraftWindow() {
        Objects.requireNonNull(this.craftWindow);
        return this.craftWindow;
    }

    @Override
    public GameWindow getGameWindow() {
        Objects.requireNonNull(this.gameWindow);
        return this.gameWindow;
    }

    @Override
    public void toInitialView() throws IllegalStateException {
        controllerMustBeSet();
        hideAllWindows();
        getInitialWindow().show();
    }

    @Override
    public void toCraftLevelView() throws IllegalStateException {
        controllerMustBeSet();
        hideAllWindows();
        getCraftWindow().show();
    }

    @Override
    public void toGameLevelView(final Level level) throws IllegalStateException {
        controllerMustBeSet();
        hideAllWindows();
        getGameWindow().show();
    }

    /**
     * Hides all windows.
     */
    private void hideAllWindows() {
        getInitialWindow().hide();
        getCraftWindow().hide();
        getGameWindow().hide();
    }

    /**
     * Throws an illegal state exception if the controller has not been set for this
     * view.
     * 
     * @throws IllegalStateException if the controller has not been set for this
     *                               view
     */
    private void controllerMustBeSet() throws IllegalStateException {
        if (this.controller == null || this.initialWindow == null || this.craftWindow == null
                || this.gameWindow == null) {
            throw new IllegalStateException("Controller must be set");
        }
    }
}
