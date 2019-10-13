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
	
	private final LevelSchema schema;
	private final int width;
	private final int height;
	private final int elementHeight;
	private final int elementWidth;
	private final int deltaMovement;
	private final Element user;
	private final List<Element> targets;
	private final List<Element> boxes;
	private final List<Element> walls;

	public LevelInstanceImpl(LevelSchema schema, int width, int height) {
		this.schema = schema;
		this.width = width;
		this.height = height;
		this.elementWidth = computeElementWidth();
		this.elementHeight = computeElementHeight();
		this.deltaMovement = (elementWidth + elementHeight) / 10;
		this.user = createUser();
		this.targets = createElementList(Type.TARGET);
		this.boxes = createElementList(Type.MOVABLE);
		this.walls = createElementList(Type.UNMOVABLE);
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
		return this.getElements();
	}

	@Override
	public List<Element> moveUserDown() {
		move(this.user, down());
		return this.getElements();
	}

	@Override
	public List<Element> moveUserLeft() {
		move(this.user, left());
		return this.getElements();
	}

	@Override
	public List<Element> moveUserRight() {
		move(this.user, right());
		return this.getElements();
	}
	
	private boolean move(Element element, BiFunction<Integer,Integer,Pair<Integer,Integer>> computeTargetPoint) {
		Pair<Integer, Integer> newPoint = computeTargetPoint.apply(element.getX(), element.getY());
		int x = newPoint.getX();
		int y = newPoint.getY();
		if (isNewPositionAcceptable(x,y)) {
			boolean notCollidedWithWalls = !collision(this.walls, x, y).isPresent();
			if (notCollidedWithWalls) {
				Optional<Element> possibleBox = collision(this.boxes, x, y);
				if (possibleBox.isPresent() && !possibleBox.get().equals(element)) {
					boolean boxMoved = move(possibleBox.get(), computeTargetPoint);
					if (boxMoved) {
						updateElementPosition(element, x, y);
						return true;
					}
				} else {
					updateElementPosition(element, x, y);
					return true;
				}
			}
		}
		return false;
	}
	
	private void updateElementPosition(Element element, int x, int y) {
		if (element.getType().equals(Type.USER)) {			
			this.user.setXPosition(x);
			this.user.setYPosition(y);
		} else if (element.getType().equals(Type.MOVABLE)) {
			int index = this.boxes.indexOf(element);
			this.boxes.get(index).setXPosition(x);
			this.boxes.get(index).setYPosition(y);			
		}
	}
	
	private boolean isNewPositionAcceptable(int x, int y) {
		return x >= 0 && x < this.width - elementWidth && y >= 0 && y < this.height - elementHeight;
	}
	
	private Optional<Element> collision(List<Element> elements, int x, int y) {
		int tolerance = 5;
		Optional<Element> collisionELement = elements.stream().filter(el ->
		y > el.getY() - elementHeight + tolerance
		&& y < el.getY() + elementHeight - tolerance
		&& x > el.getX() - elementWidth + tolerance
		&& x < el.getX() + elementWidth	- tolerance
				).findAny();
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

	private int computeElementHeight() {
		return (int) Math.round(Math.ceil(this.height / LevelSchema.N_ROWS));
	}
	
	private int computeElementWidth() {
		return (int) Math.round(Math.ceil(this.width / LevelSchema.N_ROWS));
	}

	private Element createUser() {
		Element user = null;
		for (int i = 0; i < this.schema.getSchema().size(); i++) {
			for (int j = 0; j < this.schema.getSchema().get(0).size(); j++) {
				if (this.schema.getSchema().get(i).get(j) == Type.USER) {
					user = new ElementImpl(Type.USER, 
							elementHeight, elementWidth, 
							j * elementWidth, i * elementHeight);
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
								elementHeight, elementWidth, 
								j*elementWidth, i*elementHeight));						
					}  
	 		  });
		 });
		return l;
	}

	@Override
	public List<Element> getElements() {
		return Stream.concat(Stream.concat(Stream.concat(
				Stream.of(this.user), this.targets.stream()), this.boxes.stream()), this.walls.stream())
				.collect(Collectors.toList());
	}
		
}
