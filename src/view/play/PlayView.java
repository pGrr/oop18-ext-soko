package view.play;

import view.GenericView;
import java.util.List;

import model.element.Element;

public interface PlayView extends GenericView {
	
	int getGameAreaWidth();
	
	int getGameAreaHeight();
	
	void initialize(List<Element> elements);
	
	void drawElements(List<Element> elements);

	void drawBoxesCoveringTargets(List<Element> boxesCoveringTargets);

	void showLevelFinishedDialog();

	void showGameFinishedDialog();
	
}
