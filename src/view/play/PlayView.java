package view.play;

import view.View;
import java.util.List;

import model.element.Element;

public interface PlayView extends View {
	
	int getGameAreaWidth();
	
	int getGameAreaHeight();
	
	void initialize(List<Element> elements);
	
	void drawElements(List<Element> elements);

	void drawBoxesCoveringTargets(List<Element> boxesCoveringTargets);

	void showLevelFinishedDialog();

	void showGameFinishedDialog();
	
}
