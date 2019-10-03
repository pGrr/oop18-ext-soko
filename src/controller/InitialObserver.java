package controller;

import java.util.List;

public interface InitialObserver {

	List<String> load(String path);
	
	void saveLevelSequence(List<String> levelSequence, String path);
	
	void craftLevel();
	
	void play(List<String> levelSequence);	
	
}
