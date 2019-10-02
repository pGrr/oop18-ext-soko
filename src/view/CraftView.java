package view;

public interface CraftView {
	
	void show();

	void showNoInitialPointDialog();
	
	void showTooManyInitialPointDialog();
	
	void showNoTargetDialog();
	
	void showUnequalBoxAndTargetDialog();
	
	void showUnForeSeenErrorDialog(String message);

}
