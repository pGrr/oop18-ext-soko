package view;

import controller.CraftObserver;

public interface CraftView {
	
	void show();
	
	void setObserver(CraftObserver controller);

	void showNoInitialPointDialog();
	
	void showTooManyInitialPointDialog();
	
	void showNoTargetDialog();
	
	void showUnequalBoxAndTargetDialog();
	
	void showUnForeSeenErrorDialog(String message);

}
