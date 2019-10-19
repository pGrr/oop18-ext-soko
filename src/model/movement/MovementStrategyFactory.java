package model.movement;

public interface MovementStrategyFactory {
	
	MovementStrategy up();

	MovementStrategy down();

	MovementStrategy left();

	MovementStrategy right();

	static MovementStrategyFactory createDefault(int elementEdgeSize) {
		return new MovementStrategyFactoryImpl(elementEdgeSize);
	}

}
