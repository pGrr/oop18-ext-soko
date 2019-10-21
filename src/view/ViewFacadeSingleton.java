package view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import view.craft.CraftWindow;
import view.craft.CraftWindowImpl;
import view.initial.InitialWindow;
import view.initial.InitialWindowImpl;
import view.play.GameWindow;
import view.play.GameWindowImpl;

public class ViewFacadeSingleton implements ViewFacade {
	
	private static ViewFacade SINGLETON;

	private final InitialWindow initialView;
	private final CraftWindow craftView;
	private Optional<GameWindow> gameWindow;
	
	private ViewFacadeSingleton() {
		this.craftView = new CraftWindowImpl();
		this.gameWindow = Optional.empty();
		this.initialView = new InitialWindowImpl();
	}

	public static final ViewFacade getInstance() {
		if (SINGLETON == null) {
			SINGLETON = new ViewFacadeSingleton();
		}
		return SINGLETON;
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
	public void createGameWindow(String title) {
		this.gameWindow = Optional.of(new GameWindowImpl(title));
	}

	@Override
	public GameWindow getGameWindow() {
		requirePresence(this.gameWindow);
		return this.gameWindow.get();
	}

	@Override
	public Collection<Window> getAllWindows() {
		List<Window> windows = new ArrayList<>();
		windows.add(getInitialWindow());
		windows.add(getCraftWindow());
		if (this.gameWindow.isPresent()) {
			windows.add(getGameWindow());			
		}
		return windows;
	}
	
	private <T> void requirePresence(Optional<T> o) {
		if (!o.isPresent()) {
			throw new IllegalStateException();
		}
	}
}
