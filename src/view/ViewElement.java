package view;

import java.awt.Image;
import model.Element.Type;

public interface ViewElement {
	
	public Type getType();
	
	public Image getImage();
	
	public int getXposition();

	public void setXposition(int xPosition);

	public int getYposition();

	public void setYposition(int yPosition);

	public int getWidth();
	
	public int getHeight();

}
