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

/**
 * An implementation for the {@link View} interface. Implements the Singleton
 * design pattern.
 */
public final class ViewSingleton implements View {

    private static View singleton;
    private final InitialWindow initialView;
    private final CraftWindow craftView;
    private final GameWindow gameWindow;

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
    public static View getInstance() {
        if (singleton == null) {
            singleton = new ViewSingleton();
        }
        return singleton;
    }

    @Override
    public InitialWindow getInitialWindow() {
        return this.initialView;
    }

    @Override
    public CraftWindow getCraftWindow() {
        return this.craftView;
    }

    @Override
    public GameWindow getGameWindow() {
        return this.gameWindow;
    }

    @Override
    public Collection<Window> getAllWindows() {
        List<Window> windows = new ArrayList<>();
        windows.add(getInitialWindow());
        windows.add(getCraftWindow());
        windows.add(getGameWindow());
        return windows;
    }
}
