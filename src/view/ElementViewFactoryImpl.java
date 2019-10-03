package view;

import model.ElementModel.Type;

public class ElementViewFactoryImpl implements ElementViewFactory {
	
	private static final String ICON_WALL_PATH = "wall-30px.png";
	private static final String ICON_BOX_PATH = "box-30px.png";
	private static final String ICON_TARGET_PATH = "target-30px.png";
	private static final String ICON_USER_PATH = "user-30px.png";

	private ElementViewFactoryImpl() {}

	@Override
	public ElementView createUser() {
		return new ElementViewImpl(Type.USER, ICON_USER_PATH);
	}

	@Override
	public ElementView createWall() {
		return new ElementViewImpl(Type.UNMOVABLE, ICON_WALL_PATH);
	}

	@Override
	public ElementView createBox() {
		return new ElementViewImpl(Type.MOVABLE, ICON_BOX_PATH);
	}
	
	@Override
	public ElementView createTarget() {
		return new ElementViewImpl(Type.TARGET, ICON_TARGET_PATH);
	}

	@Override
	public ElementView createEmpty() {
		return new ElementViewImpl(Type.EMPTY);
	}

}
