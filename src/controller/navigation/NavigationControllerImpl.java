package controller.navigation;

import model.level.Level;
import view.craft.CraftWindow;
import view.game.GameWindow;
import view.initial.InitialWindow;

/**
 * The default implementation of the {@link NavigationController} interface.
 */
public final class NavigationControllerImpl implements NavigationController {

    private final InitialWindow initialWindow;
    private final CraftWindow craftWindow;
    private final GameWindow gameWindow;

    /**
     * Instantiates a new navigation controller impl.
     *
     * @param initialWindow         the initial window
     * @param craftWindow           the craft window
     * @param gameWindow            the game window
     */
    public NavigationControllerImpl(final InitialWindow initialWindow, final CraftWindow craftWindow,
            final GameWindow gameWindow) {
        this.initialWindow = initialWindow;
        this.craftWindow = craftWindow;
        this.gameWindow = gameWindow;
    }

    @Override
    public InitialWindow getInitialWindow() {
        return this.initialWindow;
    }

    @Override
    public CraftWindow getCraftWindow() {
        return this.craftWindow;
    }

    @Override
    public GameWindow getGameWindow() {
        return this.gameWindow;
    }

    @Override
    public void toInitialView() {
        hideAllWindows();
        getInitialWindow().show();
    }

    @Override
    public void toCraftLevelView() {
        hideAllWindows();
        getCraftWindow().show();
    }

    @Override
    public void toGameLevelView(final Level level) {
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
}
