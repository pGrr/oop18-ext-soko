package view.play.elements;

import java.awt.Image;

import model.element.Element;

public interface ViewElement {
	
	public Element getElement();
	
	public Image getImage();
	
	public void setImage(Image resourceName);

}
