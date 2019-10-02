package view;

import model.Model.Direction;
import model.Model.Level;

public interface View {
	
	// initial view
	
	void showInitialView();
	
	void showSaveSuccessDialog();
	
	void showSaveFailedDialog();
	
	void showLoadSuccessDialog();
	
	void showLoadFailedDialog();
	
	void showLevelNotValidDialog();
	
	// craft level
	
	void showCraftLevelView();

	void showNoInitialPointDialog();
	
	void showTooManyInitialPointDialog();
	
	void showNoTargetDialog();
	
	void showUnequalBoxAndTargetDialog();
	
	void showUnForeSeenErrorDialog(String message);
	
	// play sequence
		
	void showPlayLevelView(Level level, String sequenceName);
	
	void moveFree(Direction direction);
	
	void movePushing(Direction direction);
	
	void impact();
				
	void showlevelFinishedDialog();
	
	void showSequenceFinishedDialog(boolean success);

}
