package controller;

import model.Level;

public interface CraftObserver {
	
	void saveLevel(Level level, String path);
	
	void backToInitialView();

}
