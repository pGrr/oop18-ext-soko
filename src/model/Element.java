package model;

import java.io.Serializable;

public interface Element extends Serializable {
	
	enum Type {
		USER, 
		MOVABLE, 
		UNMOVABLE,
		TARGET,
		EMPTY;
	}
	
	Type getType();
		
	int getWidth();
	
	int getHeight();
	
	int getXPosition();
	
	int getYPosition();
	
	void setPosition(int x, int y);

}
