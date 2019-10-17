package model.sequence;

import java.io.Serializable;
import java.util.List;

import model.level.LevelSchema;

public interface LevelSequence extends Iterable<LevelSchema>, Serializable {

	String getName();
	
	List<String> getNames();
	
}
