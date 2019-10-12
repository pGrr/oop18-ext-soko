package model;

public class ElementImpl implements Element {

	private static final long serialVersionUID = 477897545325345634L;
	
	private final Type type;
	private final int width;
	private final int height;
	private int xPosition;
	private int yPosition;
	
	public ElementImpl(Type type, int width, int height, int xPosition, int yPosition) {
		super();
		this.type = type;
		this.width = width;
		this.height = height;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}
	
	@Override
	public Type getType() {
		return type;
	}
	
	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	public int getXPosition() {
		return xPosition;
	}
	
	@Override
	public int getYPosition() {
		return yPosition;
	}

	@Override
	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	@Override
	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	
}
