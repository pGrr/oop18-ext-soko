package view;

import java.awt.event.ActionListener;

public interface GenericView {

	public void show();
	
	public void hide();
	
	public void close();
	
	public void showErrorDialog(String title, String message);

	void showNotifyDialog(String title, String message, ActionListener actionListener);	
}
