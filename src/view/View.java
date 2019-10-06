package view;

public interface View {

	public void show();
	
	public void hide();
	
	public void close();
	
	public void showErrorDialog(String title, String message);
	
}
