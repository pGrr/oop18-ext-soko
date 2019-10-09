package model;

import java.io.Serializable;
import java.util.List;

import model.Element.Type;
import model.LevelImpl.LevelNotValidException;

public interface LevelSequence extends Iterable<Level>, Serializable {

	String getName();
	
	List<Level> getLevelSequence();
	
	List<String> getPathList();
	
	void add(Level level);
	
	void add(List<List<Type>> typeGrid, String name) throws LevelNotValidException;
	
}
