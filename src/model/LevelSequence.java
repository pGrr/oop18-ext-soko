package model;

import java.io.Serializable;
import java.util.List;

public interface LevelSequence extends Iterable<LevelSchema>, Serializable {

	String getName();
	
	List<String> getNames();
	
}
