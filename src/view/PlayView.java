package view;

import model.Direction;
import model.Level;

public interface PlayView {
		
	void show(Level level, String sequenceName);
	
	void moveFree(Direction direction);
	
	void movePushing(Direction direction);
	
	void impact();
				
	void showlevelFinishedDialog();
	
	void showSequenceFinishedDialog(boolean success);

}
