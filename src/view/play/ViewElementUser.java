package view.play;

import model.Element;
import model.Element.Type;

public class ViewElementUser extends ViewElementBasic implements ViewElement {
	
	private static final Type TYPE = Type.USER;
	private static final String ICON = "icons/user-original.png";
	
	public ViewElementUser(Element element) {
		super(element, ICON);
		this.checkType(element, TYPE);
	}
}
