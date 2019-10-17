package model.level;

import java.io.Serializable;
import java.util.List;

import model.element.Element.Type;

public class LevelSchemaImpl implements LevelSchema, Serializable {
	
	private static final long serialVersionUID = 4977549842323813470L;

	private final String name;
	private final List<List<Type>> schema;

	public LevelSchemaImpl(String name, List<List<Type>> schema) throws LevelNotValidException {
		this.name = name;
		this.schema = validate(schema);
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public List<List<Type>> getSchema() {
		return this.schema;
	}

	private List<List<Type>> validate(List<List<Type>> schema) throws LevelNotValidException {
		if (!isGridSizeAsExpected(schema)) {
			throw new UncorrectGridSizeException();
		} else {
			long nUsers = countElements(schema, Type.USER);
			long nBoxes = countElements(schema, Type.BOX);
			long nTargets = countElements(schema, Type.TARGET);
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
		return schema;
	}

	private boolean isGridSizeAsExpected(List<List<Type>> typeGrid) {
		return typeGrid.stream().flatMap(List::stream).count() == N_ROWS * N_ROWS;
	}


	private long countElements(List<List<Type>> typeGrid, Type type) {
		return typeGrid.stream().flatMap(List::stream).filter(e -> e.equals(type)).count();
	}

	public class LevelNotValidException extends Exception {
		private static final long serialVersionUID = 7024248848064914746L;

		@Override
		public String toString() {
			return LevelSchemaImpl.this.getName() + " has not been saved because an error occurred. ";
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

}
