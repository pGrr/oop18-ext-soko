package model.movement;

import model.PairImpl;

public class MovementStrategyFactoryImpl implements MovementStrategyFactory {
	
	private final double DELTA_MOVEMENT_RELATIVE_TO_ELEMENT_EDGE_SIZE = 1;
	
	private final int deltaMovement;

	public MovementStrategyFactoryImpl(int elementEdgeSize) {
		this.deltaMovement = (int) Math.round(Math.floor(elementEdgeSize * DELTA_MOVEMENT_RELATIVE_TO_ELEMENT_EDGE_SIZE));
	}
	
	@Override
	public MovementStrategy up() {
		return element -> new PairImpl<>(element.getX(), element.getY() - deltaMovement);
	}
	
	@Override
	public MovementStrategy down() {
		return element -> new PairImpl<>(element.getX(), element.getY() + deltaMovement);
	}

	@Override
	public MovementStrategy left() {
		return element -> new PairImpl<>(element.getX() - deltaMovement, element.getY());
	}

	@Override
	public MovementStrategy right() {
		return element -> new PairImpl<>(element.getX() + deltaMovement, element.getY());
	}

}
