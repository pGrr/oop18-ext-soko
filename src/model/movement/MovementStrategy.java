package model.movement;

import model.Pair;
import model.element.Element;

public interface MovementStrategy {
	
	Pair<Integer, Integer> computeTargetPoint(Element element);

}
