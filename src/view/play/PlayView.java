package view.play;

import view.View;
import java.util.List;
import model.Element;

public interface PlayView extends View {
	
	int getPlayAreaWidth();
	
	int getPlayAreaHeight();
	
	void initialize(List<Element> elements);
	
	void showElements(List<Element> elements);

	void showBoxesCoveringTargets(List<Element> boxesCoveringTargets);
	
}
