package controller;

import java.util.List;
import model.Model.Level;

public interface Controller {
	
	void initialView();
		
	void craftLevel();
		
	void playSequence();
	
	List<Level> loadSequence(String path);
	
	void saveSequence(List<Level> levelSequence, String path);
	
	interface Element {
		
	}
}
