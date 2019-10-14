package view.play;

import java.awt.Image;

import model.Element;

public interface ViewElement {
	
	public Element getElement();
	
	public Image getImage();
	
	public void setImage(String resourceName);

}
