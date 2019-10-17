package model.movement;

public interface MovementStrategyFactory {
	
	public MovementStrategy up();

	public MovementStrategy down();

	public MovementStrategy left();

	public MovementStrategy right();


}
