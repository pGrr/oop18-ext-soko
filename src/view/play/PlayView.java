package view.play;

import view.View;
import java.util.List;
import model.Element;

public interface PlayView extends View {
	
	int getPlayAreaWidth();
	
	int getPlayAreaHeight();

	void showElements(List<Element> elements);
	
}
