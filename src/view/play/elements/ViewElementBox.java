package view.play.elements;

import model.Element;
import model.Element.Type;

public class ViewElementBox extends ViewElementBasic implements ViewElement {
	
	private static final Type TYPE = Type.MOVABLE;
	private static final String ICON = "icons/box-original.png";
	private static final String ICON_ON_TARGET = "icons/box-on-target-original.png";
	
	private boolean onTarget;

	public ViewElementBox(Element element) {
		super(element, ICON);
		this.checkType(element, TYPE);	
		this.onTarget = false;
	}
	
	public boolean isOnTarget() {
		return this.onTarget;
	}
	
	public void setOnTarget(boolean onTarget) {
		if (this.onTarget != onTarget) {
			this.onTarget = onTarget;
			this.updateImage();
		}
	}
	
	private void updateImage() {
		this.setImage(this.onTarget ? ICON_ON_TARGET : ICON);
	}
	
	

}
