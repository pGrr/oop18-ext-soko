package view;

import javax.swing.JFrame;

public interface Window {

	public void show();
	
	public void hide();
	
	public void close();
	
	public JFrame getFrame();

}
