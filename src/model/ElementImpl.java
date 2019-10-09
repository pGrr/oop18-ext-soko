package model;

public class ElementImpl implements Element {

	private static final long serialVersionUID = 477897545325345634L;
	private final Type type;
	private final int width;
	private final int height;
	private int xPosition;
	private int yPosition;
	
	public ElementImpl(Type type, int xPosition, int yPosition, int width, int height) {
		super();
		this.type = type;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = height;
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
	public void setPosition(int x, int y) {
		this.xPosition = x;
		this.yPosition = y;
	}
	
}
