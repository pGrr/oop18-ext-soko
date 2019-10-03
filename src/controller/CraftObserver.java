package controller;

import java.util.List;

import model.ElementModel.Type;

public interface CraftObserver {
	
	void saveLevel(List<List<Type>> level, String path);
	
	void backToInitialView();

}
