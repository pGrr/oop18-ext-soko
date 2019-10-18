package view.play.elements;

import java.awt.Image;
import java.util.Objects;

import model.element.Element;
import model.element.Element.Type;

public abstract class AbstractViewElement implements ViewElement {
	
	private final Element element;
	private Image image;
	
	public AbstractViewElement(Element element, Image image) {
		super();
		if (element == null || image == null) {
			throw new IllegalArgumentException();
		}
		this.element = element;
		this.image = image.getScaledInstance(element.getWidth(), element.getHeight(), Image.SCALE_DEFAULT);
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
	public void setImage(Image image) {
		this.image = image;
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
		AbstractViewElement other = (AbstractViewElement) obj;
		return element == other.element;
	}
	
	protected abstract void checkType(Element element);
	
	protected static void requireSameType(Element element, Type type) {
		if (!element.getType().equals(type)) {
			throw new IllegalArgumentException();
		}
	}
}