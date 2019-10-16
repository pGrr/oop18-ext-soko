package view.play;

import model.Element;
import model.Element.Type;

public class ViewElementWall extends ViewElementBasic implements ViewElement {
	
	private static final Type TYPE = Type.UNMOVABLE;
	private static final String ICON = "icons/wall-original.png";

	public ViewElementWall(Element element) {
		super(element, ICON);
		this.checkType(element, TYPE);
	}

}
