package model.sequence;

import java.io.Serializable;
import java.util.List;
import model.level.LevelSchema;

public interface LevelSequence extends Iterable<LevelSchema>, Serializable {

	String getSequenceName();
	
	List<String> getLevelNames();

	boolean isEmpty();
	
	void add(LevelSchema level);

	void swap(int i, int j);

	void remove(int i);

	void clear();
	
}
