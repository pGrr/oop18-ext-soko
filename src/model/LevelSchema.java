package model;

import java.io.Serializable;
import java.util.List;
import model.Element.Type;

public interface LevelSchema extends Serializable {
	
	static int N_ROWS = 25;
		
	String getName();

	List<List<Type>> getSchema();
	
}
