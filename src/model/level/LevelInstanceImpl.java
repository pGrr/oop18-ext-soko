package model.level;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import model.element.Element;
import model.element.ElementImpl;
import model.element.Element.Type;
import model.movement.MovementStrategyFactory;

public class LevelInstanceImpl implements LevelInstance {

	private final LevelSchema schema;
	private final LevelInstanceState state;
	private final MovementStrategyFactory movements;
	private final int width;
	private final int height;

	public LevelInstanceImpl(LevelSchema schema, int width, int height) {
		this.schema = schema;
		this.width = width;
		this.height = height;
		int elementEdgeSize = this.computeElementEdgeSize();
		this.movements = MovementStrategyFactory.createDefault(elementEdgeSize);
		this.state = new LevelInstanceState(height, width, elementEdgeSize, 
				createUser(elementEdgeSize), 
				createElementList(elementEdgeSize, Type.TARGET), 
				createElementList(elementEdgeSize, Type.BOX), 
				createElementList(elementEdgeSize, Type.WALL));
	}
	
	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	public List<Element> moveUserUp() {
		state.move(this.movements.up());
		return this.state.getLastModifiedElements();
	}

	@Override
	public List<Element> moveUserDown() {
		state.move(this.movements.down());
		return this.state.getLastModifiedElements();
	}

	@Override
	public List<Element> moveUserLeft() {
		state.move(this.movements.left());
		return this.state.getLastModifiedElements();
	}

	@Override
	public List<Element> moveUserRight() {
		state.move(this.movements.right());
		return this.state.getLastModifiedElements();
	}
	
	@Override
	public boolean isFinished() {
		return state.getBoxes().stream().count() == state.getBoxesOnTarget().stream().count();
	}
	
	@Override
	public List<Element> getElements() {
		return this.state.getAllElements();
	}

	@Override
	public List<Element> getBoxesOnTarget() {
		return this.state.getBoxesOnTarget();
	}
	
	private int computeElementEdgeSize() {
		return Math.round(this.height / LevelSchema.N_ROWS);
	}

	private Element createUser(int elementEdgeSize) {
		Element user = null;
		for (int i = 0; i < this.schema.getSchema().size(); i++) {
			for (int j = 0; j < this.schema.getSchema().get(0).size(); j++) {
				if (this.schema.getSchema().get(i).get(j) == Type.USER) {
					user = new ElementImpl(Type.USER, 
							elementEdgeSize, elementEdgeSize, 
							j * elementEdgeSize, i * elementEdgeSize);
				}
			}
		}
		return user;
	}
	
	private List<Element> createElementList(int elementEdgeSize, Type type) {
		List<Element> l = new ArrayList<>();
		IntStream.range(0, this.schema.getSchema().size()).forEach(i -> {
			 IntStream.range(0, this.schema.getSchema().size()).forEach(j -> {
					if (this.schema.getSchema().get(i).get(j) == type) {
						l.add(new ElementImpl(type, 
								elementEdgeSize, elementEdgeSize, 
								j*elementEdgeSize, i*elementEdgeSize));						
					}  
	 		  });
		 });
		return l;
	}
}
