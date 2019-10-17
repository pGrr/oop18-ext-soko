package model.level;

import java.io.Serializable;
import java.util.List;

import model.element.Element.Type;

public interface LevelSchema extends Serializable {
	
	static int N_ROWS = 20;
		
	String getName();

	List<List<Type>> getSchema();
	
}
