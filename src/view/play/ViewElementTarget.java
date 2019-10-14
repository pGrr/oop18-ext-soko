package view.play;

import model.Element;
import model.Element.Type;

public class ViewElementTarget extends ViewElementBasic implements ViewElement {
	
	private static final Type TYPE = Type.TARGET;
	private static final String ICON = "target-original.png";

	public ViewElementTarget(Element element) {
		super(element, ICON);
		this.checkType(element, TYPE);	
	}

}
