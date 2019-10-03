package model;

public interface ElementModel {
	
	enum Type {
		USER, 
		MOVABLE, 
		UNMOVABLE, 
		EMPTY
	}
	
	Type getType();
	
	boolean canMove();

}
