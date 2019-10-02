package controller;

import java.util.List;

import model.Model.Direction;
import model.Model.Level;

public interface Controller {
	
	// initial view
		
	List<String> load(String path);
	
	void saveLevelSequence(List<String> levelSequence, String path);
	
	void startSequence(List<String> levelSequence);	
	
	// craft level
			
	void saveLevel(Level level);
	
	void backToInitialView();
	
	// play level
	
	void move(Direction direction);
					
}
