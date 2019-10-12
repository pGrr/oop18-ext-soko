package view;

import java.util.List;
import model.Element;

public interface SokobanView {
	
	public void showInitialView();
	
	public void showCraftLevelView();
	
	public void showPlayLevelView(String name);

	int getPlayableAreaWidth();
	
	int getPlayableAreaHeight();
	
	void showElements(List<Element> elements);
	
}
