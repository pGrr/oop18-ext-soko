package view.play.elements;

import static view.play.elements.ViewElementConstants.ICON_BOX;
import static view.play.elements.ViewElementConstants.ICON_BOX_ON_TARGET;
import static view.play.elements.ViewElementConstants.ICON_TARGET;
import static view.play.elements.ViewElementConstants.ICON_USER;
import static view.play.elements.ViewElementConstants.ICON_WALL;

import java.awt.Image;

import model.element.Element;
import model.element.Element.Type;

public class ViewElementFactory {
	
	public static final ViewElementUser createUser(Element element) {
		return new ViewElementUser(element);
	}
	
	public static final ViewElementTarget createTarget(Element element) {
		return new ViewElementTarget(element);
	}
	
	public static final ViewElementWall createWall(Element element) {
		return new ViewElementWall(element);
	}
	
	public static final ViewElementBox createBox(Element element) {
		return new ViewElementBox(element);
	}
	
	public static class ViewElementUser extends AbstractViewElement implements ViewElement {
			
		public ViewElementUser(Element element) {
			super(element, ICON_USER);
		}

		@Override
		protected void checkType(Element element) {
			requireSameType(element, Type.USER);
		}
	
	}
	
	public static class ViewElementTarget extends AbstractViewElement implements ViewElement {
		
		public ViewElementTarget(Element element) {
			super(element, ICON_TARGET);
		}
		
		@Override
		protected void checkType(Element element) {
			requireSameType(element, Type.TARGET);
		}
	
	}
	
	public static class ViewElementWall extends AbstractViewElement implements ViewElement {
		
		public ViewElementWall(Element element) {
			super(element, ICON_WALL);
		}
		
		@Override
		protected void checkType(Element element) {
			requireSameType(element, Type.WALL);
		}
		
	}
	
	public static class ViewElementBox extends AbstractViewElement implements ViewElement {
		
		private boolean onTarget;
		private final Image defaultImage;
		private final Image onTargetImage;

		public ViewElementBox(Element element) {
			super(element, ICON_BOX);
			this.defaultImage = ICON_BOX.getScaledInstance(element.getWidth(), element.getHeight(), Image.SCALE_DEFAULT);
			this.onTargetImage = ICON_BOX_ON_TARGET.getScaledInstance(element.getWidth(), element.getHeight(), Image.SCALE_DEFAULT);
			this.onTarget = false;
		}
		
		@Override
		protected void checkType(Element element) {
			requireSameType(element, Type.BOX);
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
			this.setImage(this.onTarget ? onTargetImage : defaultImage);
		}
	}
}