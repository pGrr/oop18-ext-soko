package model;

import java.io.Serializable;
import java.util.List;

import model.Element.Type;
import model.LevelImpl.LevelNotValidException;

public interface LevelSequence extends Serializable {

	String getName();
	
	List<Level> getSequence();
	
	void add(Level level);
	
	void add(List<List<Type>> typeGrid, String name) throws LevelNotValidException;
	
}
