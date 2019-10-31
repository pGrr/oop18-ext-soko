package controller.navigation;

import model.level.Level;
import view.View;

/**
 * The default implementation of the {@link NavigationController} interface.
 * Implements the singleton design pattern.
 */
public final class NavigationControllerSingleton implements NavigationController {

    private static NavigationController singleton;

    private NavigationControllerSingleton() {
    }

    /**
     * Gets the single instance of NavigationControllerImpl.
     *
     * @return single instance of NavigationControllerImpl
     */
    public static NavigationController getInstance() {
        if (singleton == null) {
            singleton = new NavigationControllerSingleton();
        }
        return singleton;
    }

    @Override
    public void toInitialView() {
        hideAllWindows();
        View.getInstance().getInitialWindow().show();
    }

    @Override
    public void toCraftLevelView() {
        hideAllWindows();
        View.getInstance().getCraftWindow().show();
    }

    @Override
    public void toGameLevelView(final Level level) {
        hideAllWindows();
        View.getInstance().getGameWindow().show();
    }

    /*
     * Hide all windows.
     */
    private void hideAllWindows() {
        View.getInstance().getAllWindows().forEach(w -> w.hide());
    }
}
