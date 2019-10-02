package model;

public interface Level {
	
	// initial view

	boolean isLevelValid(Level level);
	
	// Craft level
	
	boolean isThereInitialPoint(Level level);
	
	boolean isInitialPointSingular(Level level);
	
	boolean isBoxAndTargetNumberEqual(Level level);
	
	boolean isThereTarget(Level level);
	
	// play
		
	void play(Level level);
	
	boolean canMoveFree(Direction direction);
	
	boolean canMovePushing(Direction direction);
	
	void moveFreely(Direction direction);
	
	void movePushing(Direction direction);
	
	boolean isPushedObjectOnTarget();
	
	void unValidateTarget(Position position);
	
	void validateTarget(Position position);
	
	boolean areAllTargetValidated();

}
