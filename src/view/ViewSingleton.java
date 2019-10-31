package view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import view.craft.CraftWindow;
import view.craft.CraftWindowImpl;
import view.game.GameWindow;
import view.game.GameWindowImpl;
import view.initial.InitialWindow;
import view.initial.InitialWindowImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class ViewSingleton.
 */
public class ViewSingleton implements View {

    /** The singleton. */
    private static View SINGLETON;

    /** The initial view. */
    private final InitialWindow initialView;

    /** The craft view. */
    private final CraftWindow craftView;

    /** The game window. */
    private final GameWindow gameWindow;

    /**
     * Instantiates a new view singleton.
     */
    private ViewSingleton() {
        this.craftView = new CraftWindowImpl();
        this.gameWindow = new GameWindowImpl();
        this.initialView = new InitialWindowImpl();
    }

    /**
     * Gets the single instance of ViewSingleton.
     *
     * @return single instance of ViewSingleton
     */
    public static final View getInstance() {
        if (SINGLETON == null) {
            SINGLETON = new ViewSingleton();
        }
        return SINGLETON;
    }

    /**
     * Gets the initial window.
     *
     * @return the initial window
     */
    @Override
    public InitialWindow getInitialWindow() {
        return this.initialView;
    }

    /**
     * Gets the craft window.
     *
     * @return the craft window
     */
    @Override
    public CraftWindow getCraftWindow() {
        return this.craftView;
    }

    /**
     * Gets the game window.
     *
     * @return the game window
     */
    @Override
    public GameWindow getGameWindow() {
        return this.gameWindow;
    }

    /**
     * Gets the window on top.
     *
     * @return the window on top
     */
    @Override
    public Window getWindowOnTop() {
        return this.getAllWindows().stream().filter(w -> w.getFrame().isVisible()).findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Gets the all windows.
     *
     * @return the all windows
     */
    @Override
    public Collection<Window> getAllWindows() {
        List<Window> windows = new ArrayList<>();
        windows.add(getInitialWindow());
        windows.add(getCraftWindow());
        windows.add(getGameWindow());
        return windows;
    }
}
