package view;

public interface ElementViewFactory {
	
	ElementView createUser();
	
	ElementView createWall();
	
	ElementView createBox();
	
	ElementView createTarget();
	
	ElementView createEmpty();

}
