package view;

import java.util.Collection;
import view.craft.CraftWindow;
import view.initial.InitialWindow;
import view.play.GameWindow;

public interface ViewFacade {

	InitialWindow getInitialWindow();
	
	CraftWindow getCraftWindow();
	
	void createGameWindow(String title);
		
	GameWindow getGameWindow();

	Collection<Window> getAllWindows();
	
	static ViewFacade getInstance() {
		return ViewFacadeSingleton.getInstance();
	}

}