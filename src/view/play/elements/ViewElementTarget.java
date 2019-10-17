package view.play.elements;

import model.element.Element;
import model.element.Element.Type;

public class ViewElementTarget extends ViewElementBasic implements ViewElement {
	
	private static final Type TYPE = Type.TARGET;
	private static final String ICON = "icons/target-original.png";

	public ViewElementTarget(Element element) {
		super(element, ICON);
		this.checkType(element, TYPE);	
	}

}
