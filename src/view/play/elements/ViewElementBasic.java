package view.play.elements;

import static view.Components.createImageIcon;
import java.awt.Image;
import java.util.Objects;

import model.Element;
import model.Element.Type;

public class ViewElementBasic implements ViewElement {
	
	private final Element element;
	private Image image;
	
	public ViewElementBasic(Element element, String iconResourceName) {
		super();
		if (element == null || iconResourceName == null || iconResourceName.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.element = element;
		this.image = createScaledImageFromResource(iconResourceName, element.getWidth(), element.getHeight());
	}
	
	@Override
	public Element getElement() {
		return this.element;
	}

	@Override
	public Image getImage() {
		return this.image;
	}
	
	@Override
	public void setImage(String resourceName) {
		this.image = createScaledImageFromResource(resourceName, this.element.getWidth(), this.element.getHeight());
	}

	protected Image createScaledImageFromResource(String resourceName, int width, int height) {
		return createImageIcon(resourceName).getImage()
				.getScaledInstance(width, height, Image.SCALE_FAST); 
	}
	
	protected void checkType(Element element, Type type) {
		if (element == null || !element.getType().equals(type)) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(element);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ViewElementBasic other = (ViewElementBasic) obj;
		return element == other.element;
	}

	

}