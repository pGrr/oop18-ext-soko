package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import model.Element.Type;

public class LevelInstanceImpl implements LevelInstance {
	
	private final double DELTA_MOVEMENT_RELATIVE_TO_ELEMENT_EDGE_SIZE = 1;
	private final double COLLISION_TOLERANCE_PX = 5;
	
	private final LevelSchema schema;
	private final int width;
	private final int height;
	private final int elementEdgeSize;
	private final int deltaMovement;
	private final Element user;
	private final List<Element> targets;
	private final List<Element> boxes;
	private final List<Element> walls;
	private final List<Element> lastModified;

	public LevelInstanceImpl(LevelSchema schema, int width, int height) {
		this.schema = schema;
		this.width = width;
		this.height = height;
		this.elementEdgeSize = computeElementEdgeSize();
		this.deltaMovement = (int) Math.round(Math.floor(elementEdgeSize * DELTA_MOVEMENT_RELATIVE_TO_ELEMENT_EDGE_SIZE));
		this.user = createUser();
		this.targets = createElementList(Type.TARGET);
		this.boxes = createElementList(Type.MOVABLE);
		this.walls = createElementList(Type.UNMOVABLE);
		this.lastModified = new ArrayList<>();
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
		move(this.user, up());
		return this.lastModified;
	}

	@Override
	public List<Element> moveUserDown() {
		move(this.user, down());
		return this.lastModified;
	}

	@Override
	public List<Element> moveUserLeft() {
		move(this.user, left());
		return this.lastModified;
	}

	@Override
	public List<Element> moveUserRight() {
		move(this.user, right());
		return this.lastModified;
	}
	
	@Override
	public List<Element> getElements() {
		return Stream.concat(Stream.concat(Stream.concat(
				Stream.of(this.user), this.targets.stream()), this.boxes.stream()), this.walls.stream())
				.collect(Collectors.toList());
	}

	@Override
	public List<Element> getBoxesOnTarget() {
		return this.targets.stream()
						   .map(target -> collision(target, this.boxes))
						   .filter(Optional::isPresent)
						   .map(Optional::get)
						   .collect(Collectors.toList());
	}
	
	private void move(Element element, BiFunction<Integer,Integer,Pair<Integer,Integer>> computeTargetPoint) {
		this.lastModified.clear();
		Pair<Integer, Integer> newPoint = computeTargetPoint.apply(element.getX(), element.getY());
		if (element.getType().equals(Type.USER)) {
			if (isNewPositionAcceptable(newPoint.getX(), newPoint.getY())) {
				List<Element> wallsAndBoxes = Stream.concat(this.walls.stream(), this.boxes.stream()).collect(Collectors.toList());
				Optional<Element> obstacle = collision(element, wallsAndBoxes, computeTargetPoint);
				if (!obstacle.isPresent()) {
					updateElementPosition(element, computeTargetPoint);
				} else if (obstacle.get().getType().equals(Type.MOVABLE)) {
					Element box = obstacle.get();
					Optional<Element> boxObstacle = collision(box, wallsAndBoxes, computeTargetPoint);
					if (!boxObstacle.isPresent()) {
						updateElementPosition(box, computeTargetPoint);
						updateElementPosition(element, computeTargetPoint);
					}
				}
			}
		}
	}
	
	
	private void updateElementPosition(Element element, BiFunction<Integer,Integer,Pair<Integer,Integer>> computeTargetPoint) {
		Pair<Integer, Integer> newPoint = computeTargetPoint.apply(element.getX(), element.getY());
		if (element.getType().equals(Type.USER)) {			
			this.user.setXPosition(newPoint.getX());
			this.user.setYPosition(newPoint.getY());
		} else if (element.getType().equals(Type.MOVABLE)) {
			int index = this.boxes.indexOf(element);
			this.boxes.get(index).setXPosition(newPoint.getX());
			this.boxes.get(index).setYPosition(newPoint.getY());			
		}
	}
	
	private boolean isNewPositionAcceptable(int x, int y) {
		return x >= 0 && x < this.width - elementEdgeSize && y >= 0 && y < this.height - elementEdgeSize;
	}
	
	private Optional<Element> collision(Element element, List<Element> others, 
			BiFunction<Integer,Integer,Pair<Integer,Integer>> computeTargetPoint) {
		Pair<Integer, Integer> newPoint = computeTargetPoint.apply(element.getX(), element.getY());
		Optional<Element> collisionELement = others.stream()
												   .filter(el ->
			newPoint.getY() > el.getY() - elementEdgeSize + COLLISION_TOLERANCE_PX
			&& newPoint.getY() < el.getY() + elementEdgeSize - COLLISION_TOLERANCE_PX
			&& newPoint.getX() > el.getX() - elementEdgeSize + COLLISION_TOLERANCE_PX
			&& newPoint.getX() < el.getX() + elementEdgeSize - COLLISION_TOLERANCE_PX).findAny();
		return collisionELement;
	}
	
	private Optional<Element> collision(Element element, List<Element> others) {
		Optional<Element> collisionELement = others.stream()
												   .filter(other ->
			element.getY() > other.getY() - elementEdgeSize + COLLISION_TOLERANCE_PX
			&& element.getY() < other.getY() + elementEdgeSize - COLLISION_TOLERANCE_PX
			&& element.getX() > other.getX() - elementEdgeSize + COLLISION_TOLERANCE_PX
			&& element.getX() < other.getX() + elementEdgeSize - COLLISION_TOLERANCE_PX).findAny();
		return collisionELement;
	}
	
	private BiFunction<Integer,Integer,Pair<Integer,Integer>> up() {
		return (x, y) -> new PairImpl<>(x, y - deltaMovement);
	}
	
	private BiFunction<Integer,Integer,Pair<Integer,Integer>> down() {
		return (x, y) -> new PairImpl<>(x, y + deltaMovement);
	}
	
	private BiFunction<Integer,Integer,Pair<Integer,Integer>> left() {
		return (x, y) -> new PairImpl<>(x - deltaMovement, y);
	}
	
	private BiFunction<Integer,Integer,Pair<Integer,Integer>> right() {
		return (x, y) -> new PairImpl<>(x + deltaMovement, y);
	}

	private int computeElementEdgeSize() {
		return (int) Math.round(Math.ceil(this.width / LevelSchema.N_ROWS));
	}

	private Element createUser() {
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
	
	private List<Element> createElementList(Type type) {
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

	@Override
	public boolean isFinished() {
		return this.boxes.stream().count() == getBoxesOnTarget().stream().count();
	}
		
}
