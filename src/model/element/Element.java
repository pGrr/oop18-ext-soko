package model.element;

import java.io.Serializable;

public interface Element extends Serializable {
	
	enum Type {
		USER, 
		BOX, 
		WALL,
		TARGET,
		EMPTY;
	}
	
	Type getType();
		
	int getWidth();
	
	int getHeight();

	int getX();
	
	int getY();

	void setXPosition(int xPosition);

	void setYPosition(int yPosition);	
	
	@Override
	public boolean equals(Object obj);
	
	@Override
	public int hashCode();
}
