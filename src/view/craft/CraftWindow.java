package view.craft;

import view.Window;

public interface CraftWindow extends Window {

	void showIOErrorDialog();
	
	void showClassNotFoundErrorDialog();
	
	void showLevelInvalidDialog(String cause);
	
}
