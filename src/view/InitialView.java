package view;

import controller.InitialObserver;

public interface InitialView {
	
	void show();
	
	void setObserver(InitialObserver controller);
	
	void showSaveSuccessDialog();
	
	void showSaveFailedDialog();
	
	void showLoadSuccessDialog();
	
	void showLoadFailedDialog();
	
	void showLevelNotValidDialog();

}
