package model;

import java.io.Serializable;
import java.util.List;

import model.Element.Type;
import model.LevelSchemaImpl.LevelNotValidException;

public interface LevelSequence extends Iterable<LevelSchema>, Serializable {

	String getName();
	
	List<String> getNames();
	
}
