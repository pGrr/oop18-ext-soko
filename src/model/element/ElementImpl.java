package model.element;

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
	
	public ElementImpl(Element element) {
		this.type = element.getType();
		this.width = element.getWidth();
		this.height = element.getHeight();
		this.xPosition = element.getX();
		this.yPosition = element.getY();	
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
	public int getX() {
		return xPosition;
	}
	
	@Override
	public int getY() {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + width;
		result = prime * result + xPosition;
		result = prime * result + yPosition;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ElementImpl other = (ElementImpl) obj;
		if (height != other.height)
			return false;
		if (type != other.type)
			return false;
		if (width != other.width)
			return false;
		if (xPosition != other.xPosition)
			return false;
		if (yPosition != other.yPosition)
			return false;
		return true;
	}
	
}
