package model.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import model.Pair;
import model.element.Element;
import model.element.Element.Type;
import model.movement.MovementStrategy;

public class LevelInstanceState {
	
	private final double COLLISION_TOLERANCE_PX = 5;

	private final Element user;
	private final List<Element> targets;
	private final List<Element> boxes;
	private final List<Element> walls;
	private final List<Element> lastModified;
	private final int elementEdgeSize;
	private final int width;
	private final int height;
	
	public LevelInstanceState(int height, int width, int elementEdgeSize, 
			Element user, List<Element> targets, List<Element> boxes, List<Element> walls) {
		this.width = width;
		this.height = height;
		this.elementEdgeSize = elementEdgeSize;
		this.user = user;
		this.targets = targets;
		this.boxes = boxes;
		this.walls = walls;
		this.lastModified = new ArrayList<>();
	}
	
	public List<Element> getAllElements() {
		return Stream.concat(
				Stream.concat(
						Stream.concat(
								Stream.of(this.user), this.targets.stream()), 
						this.boxes.stream()), 
				this.walls.stream()).collect(Collectors.toList());
	}
	
	public List<Element> getLastModifiedElements() {
		return Collections.unmodifiableList(this.lastModified);
	}
	
	public List<Element> getBoxes() {
		return this.boxes.stream()
						 .collect(Collectors.toList());
	}

	public List<Element> getBoxesOnTarget() {
		return this.targets.stream()
						   .map(target -> collision(target, this.boxes))
						   .filter(Optional::isPresent)
						   .map(Optional::get)
						   .collect(Collectors.toList());
	}
	
	public void move(MovementStrategy movement) {
		this.move(this.user, movement);
	}
	
	private void move(Element element, MovementStrategy movement) {
		this.lastModified.clear();
		Pair<Integer, Integer> newPoint = movement.computeTargetPoint(element);
		if (element.getType().equals(Type.USER)) {
			if (isNewPositionAcceptable(newPoint.getX(), newPoint.getY())) {
				List<Element> wallsAndBoxes = Stream.concat(this.walls.stream(), this.boxes.stream()).collect(Collectors.toList());
				Optional<Element> obstacle = collision(element, wallsAndBoxes, movement);
				if (!obstacle.isPresent()) {
					updateElementPosition(element, movement);
				} else if (obstacle.get().getType().equals(Type.BOX)) {
					Element box = obstacle.get();
					Optional<Element> boxObstacle = collision(box, wallsAndBoxes, movement);
					if (!boxObstacle.isPresent()) {
						updateElementPosition(box, movement);
						updateElementPosition(element, movement);
					}
				}
			}
		}
	}
	
	private boolean isNewPositionAcceptable(int x, int y) {
		return x >= 0 && x < this.width - elementEdgeSize && y >= 0 && y < this.height - elementEdgeSize;
	}
	
	private Optional<Element> collision(Element element, List<Element> others, 
			MovementStrategy movement) {
		Pair<Integer, Integer> newPoint = movement.computeTargetPoint(element);
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
	
	private void updateElementPosition(Element element, MovementStrategy movement) {
		Pair<Integer, Integer> newPoint = movement.computeTargetPoint(element);
		if (element.getType().equals(Type.USER)) {			
			this.user.setXPosition(newPoint.getX());
			this.user.setYPosition(newPoint.getY());
		} else if (element.getType().equals(Type.BOX)) {
			int index = this.boxes.indexOf(element);
			this.boxes.get(index).setXPosition(newPoint.getX());
			this.boxes.get(index).setYPosition(newPoint.getY());			
		}
	}
}
