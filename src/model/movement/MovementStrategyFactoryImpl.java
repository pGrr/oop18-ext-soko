package model.movement;

import model.PairImpl;

public class MovementStrategyFactoryImpl implements MovementStrategyFactory {
	
	private final int deltaMovement;

	public MovementStrategyFactoryImpl(int deltaMovement) {
		this.deltaMovement = deltaMovement;
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
