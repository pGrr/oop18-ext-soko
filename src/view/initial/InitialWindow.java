package view.initial;

import view.Window;

public interface InitialWindow extends Window {
	
	void showIOErrorDialog();
	
	void showClassNotFoundErrorDialog();
	
	void showLevelInvalidDialog(String cause);
	
}
