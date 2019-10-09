package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import model.Element.Type;

public class LevelImpl implements Level, Serializable {

	private static final long serialVersionUID = 4977549842323813470L;
	
	private final String name;
	private List<List<Type>> grid;
	private transient Optional<Integer> width;
	private transient Optional<Integer> height;

	public LevelImpl() {
		this.grid = initializeGrid(N_ROWS);
		this.name = new String();
		width = Optional.empty();
		height = Optional.empty();
	}
	
	public LevelImpl(List<List<Type>> typeGrid, String name) throws LevelNotValidException {
		this.name = name;
		this.accept(typeGrid);
		width = Optional.empty();
		height = Optional.empty();
	}

	@Override
	public void accept(List<List<Type>> typeGrid) throws LevelNotValidException {
		if (!isGridSizeAsExpected(typeGrid)) {
			throw new UncorrectGridSizeException();
		} else {
			long nUsers = countElements(typeGrid, Type.USER);
			long nBoxes = countElements(typeGrid, Type.MOVABLE);
			long nTargets = countElements(typeGrid, Type.TARGET);
			if (nUsers <= 0) {
				throw new NoInitialPointException();
			} else if (nUsers > 1) {
				throw new MultipleInitialPointException();
			} else if (nTargets <= 0) {
				throw new NoTargetException();
			} else if (nTargets != nBoxes) {
				throw new UnequalBoxAndTargetNumberException();
			}
		}
		this.grid = typeGrid;
	}
	
	private boolean isGridSizeAsExpected(List<List<Type>> typeGrid) {
		return typeGrid.stream().flatMap(List::stream).count() == N_ROWS * N_ROWS;
	}

	@Override
	public Type get(int rowIndex, int columnIndex) {
		return this.grid.get(rowIndex).get(columnIndex);
	}

	@Override
	public void set(Type element, int rowIndex, int columnIndex) {
		this.grid.get(rowIndex).set(columnIndex, element);
	}
	
	@Override
	public String getName() {
		return name;
	}

	private List<List<Type>> initializeGrid(int nRows) {
		List<List<Type>> grid = new ArrayList<>();
		IntStream.range(0, nRows)
		 .forEach(i -> {
			grid.add(new ArrayList<>());
			IntStream.range(0, nRows)
					 .forEach(j -> {
						 grid.get(i).add(Type.EMPTY);
					 });
		 });
		return grid;
	}
	
	private long countElements(List<List<Type>> typeGrid, Type type) {
		return  typeGrid.stream()
						.flatMap(List::stream)
						.filter(e -> e.equals(type))
						.count();
	}
	
	public class LevelNotValidException extends Exception {
		private static final long serialVersionUID = 7024248848064914746L;
		@Override
		public String toString() {
			return LevelImpl.this.getName() + " has not been saved because an error occurred. ";
		}
	}
	
	public class UncorrectGridSizeException extends LevelNotValidException {
		private static final long serialVersionUID = 2049339700366934644L;
		@Override
		public String toString() {
			return super.toString() + "Grid size is not as expected.";
		}
	}

	public class NoInitialPointException extends LevelNotValidException {
		private static final long serialVersionUID = -1894510007823585149L;

		@Override
		public String toString() {
			return super.toString() + "No initial point was found.";
		}
	}
	
	public class MultipleInitialPointException extends LevelNotValidException {
		private static final long serialVersionUID = -3154665343553697380L;
		@Override
		public String toString() {
			return super.toString() + "Initial point is not singular.";
		}
	}
	
	public class NoTargetException extends LevelNotValidException {
		private static final long serialVersionUID = 3081257022270340114L;
		@Override
		public String toString() {
			return super.toString() + "No target was found.";
		}
	}
	
	public class UnequalBoxAndTargetNumberException extends LevelNotValidException {
		private static final long serialVersionUID = -594628595864277803L;
		@Override
		public String toString() {
			return super.toString() + "Boxes and targets quantity is not equal.";
		}
	}

	@Override
	public List<List<Type>> getTypeGrid() {
		return this.grid;
	}

	@Override
	public Optional<Integer> getWidth() {
		return width;
	}
	
	@Override
	public void setWidth(int width) {
		this.width = Optional.of(width);
	}

	@Override
	public Optional<Integer> getHeight() {
		return height;
	}

	@Override
	public void setHeight(int height) {
		this.height = Optional.of(height);
	}

}
