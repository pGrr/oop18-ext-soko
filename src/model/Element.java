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
}
