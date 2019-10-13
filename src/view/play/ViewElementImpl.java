package view.play;

import java.awt.Image;
import model.Element.Type;
import view.Views;

public class ViewElementImpl implements ViewElement {

	private static final String ICON_USER_PATH = "user-original.png";
	private static final String ICON_BOX_PATH = "box-original.png";
	private static final String ICON_TARGET_PATH = "target-original.png";
	private static final String ICON_WALL_PATH = "wall-original.png";
	
	private final Type type;
	private final Image image;
	private final int width;
	private final int height;
	private int xPosition;
	private int yPosition;
	
	public ViewElementImpl(Type type, int width, int height, int xPosition, int yPosition) {
		super();
		this.type = type;
		this.width = width;
		this.height = height;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.image = getTypeImage(type);
	}	
	
	public final int getXposition() {
		return xPosition;
	}

	public final void setXposition(int xPosition) {
		this.xPosition = xPosition;
	}

	public final int getYposition() {
		return yPosition;
	}

	public final void setYposition(int yPosition) {
		this.yPosition = yPosition;
	}

	public static final String getIconUserPath() {
		return ICON_USER_PATH;
	}

	public static final String getIconBoxPath() {
		return ICON_BOX_PATH;
	}

	public static final String getIconTargetPath() {
		return ICON_TARGET_PATH;
	}
	
	public final Type getType() {
		return type;
	}
	
	public final Image getImage() {
		return image;
	}
	
	public final int getWidth() {
		return width;
	}
	
	public final int getHeight() {
		return height;
	}
	
	private Image getTypeImage(Type type) {
		if (type == Type.USER) {
			return Views.createImageIcon(ICON_USER_PATH).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		} else if (type == Type.MOVABLE) {
			return Views.createImageIcon(ICON_BOX_PATH).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		} else if (type == Type.TARGET) {
			return Views.createImageIcon(ICON_TARGET_PATH).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		} else if (type == Type.UNMOVABLE) {
			return Views.createImageIcon(ICON_WALL_PATH).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		} else {
			return Views.createImageIcon("").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		}
	}
	
}