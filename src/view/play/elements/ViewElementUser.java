package view.play.elements;

import model.element.Element;
import model.element.Element.Type;

public class ViewElementUser extends ViewElementBasic implements ViewElement {
	
	private static final Type TYPE = Type.USER;
	private static final String ICON = "icons/user-original.png";
	
	public ViewElementUser(Element element) {
		super(element, ICON);
		this.checkType(element, TYPE);
	}
}
