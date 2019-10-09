package model;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import model.Element.Type;
import model.LevelImpl.LevelNotValidException;

public interface Level extends Serializable {
	
	public static int N_ROWS = 15;
		
	String getName();

	void accept(List<List<Type>> typeGrid) throws LevelNotValidException;
	
	List<List<Type>> getTypeGrid();

	Type get(int rowIndex, int columnIndex);
	
	void set(Type element, int rowIndex, int columnIndex);	
	
	Optional<Integer> getWidth();

	void setWidth(int width);
		
	Optional<Integer> getHeight();

	void setHeight(int height);
	
}
