package view.play.elements;

import model.element.Element;
import model.element.Element.Type;

public class ViewElementWall extends ViewElementBasic implements ViewElement {
	
	private static final Type TYPE = Type.WALL;
	private static final String ICON = "icons/wall-original.png";

	public ViewElementWall(Element element) {
		super(element, ICON);
		this.checkType(element, TYPE);
	}

}
