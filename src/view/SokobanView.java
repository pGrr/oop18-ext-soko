package view;

import java.util.List;

import model.Element.Type;

public interface SokobanView {
	
	public void showInitialView();
	
	public void showCraftLevelView();
	
	public void showPlayLevelView(String name, List<List<Type>> typeGrid);
	
}
