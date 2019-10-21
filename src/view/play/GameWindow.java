package view.play;

import view.Window;
import java.util.List;

import model.element.Element;

public interface GameWindow extends Window {
	
	int getGameAreaWidth();
	
	int getGameAreaHeight();
			
	void drawElements(List<Element> elements);

	void drawBoxesCoveringTargets(List<Element> boxesCoveringTargets);
		
	void showLevelFinishedDialog();
	
	void showGameFinishedDialog();
}
