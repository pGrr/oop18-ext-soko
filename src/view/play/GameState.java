package view.play;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import model.element.Element;
import model.element.Element.Type;
import view.play.elements.ViewElement;
import view.play.elements.ViewElementFactory;
import view.play.elements.ViewElementFactory.ViewElementBox;

public class GameState {

	private final List<ViewElement> elements;
	private final Set<ViewElementBox> boxesOnTarget;
	
	public GameState(List<Element> elements) {
		super();
		Objects.requireNonNull(elements);
		this.elements = elements.stream()
								.map(this::translateElement)
								.collect(Collectors.toList());
		this.boxesOnTarget = new HashSet<>();
	}

	public List<ViewElement> getAllElements() {
		return this.elements;
	}
	
	public ViewElement translateElement(Element element) {
		if (element.getType().equals(Type.USER)) {
			return ViewElementFactory.createUser(element);
		} else if (element.getType().equals(Type.TARGET)) {
			return ViewElementFactory.createTarget(element);
		} else if (element.getType().equals(Type.BOX)) {
			return ViewElementFactory.createBox(element);
		} else if (element.getType().equals(Type.WALL)) {
			return ViewElementFactory.createWall(element);
		}
		throw new IllegalArgumentException();
	}
	
	public void updateBoxesOnTarget(List<Element> newBoxesOnTarget) {
		if (newBoxesOnTarget.isEmpty()) {
			clearOnTarget();
		} else {
			List<ViewElementBox> newViewBoxesOnTarget = newBoxesOnTarget.stream()
					  .map(this::translateElement)
					  .map(b -> (ViewElementBox)b)
					  .collect(Collectors.toList());
			addNewOnes(newViewBoxesOnTarget);
			removeOldOnes(newViewBoxesOnTarget);
		}
	}
	
	private void clearOnTarget() {
		this.boxesOnTarget.forEach(box -> {
			int index = this.elements.indexOf(box);
			((ViewElementBox)this.elements.get(index)).setOnTarget(false);
			this.boxesOnTarget.remove(box);				
		});
	}
	
	private void addNewOnes(List<ViewElementBox> newViewBoxesOnTarget) {
		newViewBoxesOnTarget.forEach(box -> {
			int index = this.elements.indexOf(box);
			if (!this.boxesOnTarget.contains(this.elements.get(index))) {
				((ViewElementBox)this.elements.get(index)).setOnTarget(true);
				this.boxesOnTarget.add(box);
			} 				
		});
	}
	
	private void removeOldOnes(List<ViewElementBox> newViewBoxesOnTarget) {
		this.boxesOnTarget.forEach(box -> {
			int index = this.elements.indexOf(box);
			if (!newViewBoxesOnTarget.contains(box)) {
				((ViewElementBox)this.elements.get(index)).setOnTarget(false);
				this.boxesOnTarget.remove(box);					
			}
		});
	}
}
