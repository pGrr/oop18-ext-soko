package model;

public interface ElementModel {
	
	enum Type {
		USER, 
		MOVABLE, 
		UNMOVABLE,
		TARGET,
		EMPTY
	}
	
	Type getType();
	
	boolean canMove();

}
