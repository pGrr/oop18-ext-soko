package model;

import java.io.Serializable;
import java.util.List;

import model.Element.Type;
import model.LevelImpl.LevelNotValidException;

public interface Level extends Serializable {
		
	String getName();
	
	Type get(int rowIndex, int columnIndex);
	
	void set(Type element, int rowIndex, int columnIndex);
	
	void accept(List<List<Type>> typeGrid) throws LevelNotValidException;
	
	List<List<Type>> getTypeGrid();
	
}
