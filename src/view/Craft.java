package view;

public interface Craft {
	
	void show();

	void showNoInitialPointDialog();
	
	void showTooManyInitialPointDialog();
	
	void showNoTargetDialog();
	
	void showUnequalBoxAndTargetDialog();
	
	void showUnForeSeenErrorDialog(String message);

}
