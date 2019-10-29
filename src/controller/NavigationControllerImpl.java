package controller;

import model.Level;
import view.View;

// TODO: Auto-generated Javadoc
/**
 * The Class NavigationControllerImpl.
 */
public class NavigationControllerImpl implements NavigationController {

    /** The singleton. */
    private static NavigationController SINGLETON;

    /**
     * Instantiates a new navigation controller impl.
     */
    private NavigationControllerImpl() {
    }

    /**
     * Gets the single instance of NavigationControllerImpl.
     *
     * @return single instance of NavigationControllerImpl
     */
    public static final NavigationController getInstance() {
        if (SINGLETON == null) {
            SINGLETON = new NavigationControllerImpl();
        }
        return SINGLETON;
    }

    /**
     * To initial view.
     */
    @Override
    public void toInitialView() {
        hideAllWindows();
        View.getInstance().getInitialWindow().show();
    }

    /**
     * To craft level.
     */
    @Override
    public void toCraftLevel() {
        hideAllWindows();
        View.getInstance().getCraftWindow().show();
    }

    /**
     * To game level.
     *
     * @param level the level
     */
    @Override
    public void toGameLevel(Level level) {
        hideAllWindows();
        View.getInstance().getGameWindow().show();
    }

    /**
     * Hide all windows.
     */
    private void hideAllWindows() {
        View.getInstance().getAllWindows().forEach(w -> w.hide());
    }
}