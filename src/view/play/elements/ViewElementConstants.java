package view.play.elements;

import java.awt.Image;

import view.GuiComponentFactory;
import view.GuiComponentFactoryImpl;

public class ViewElementConstants {
	
	public static final GuiComponentFactory COMPONENT_FACTORY = GuiComponentFactoryImpl.getInstance();
	public static final Image ICON_BOX = createImage("icons/box-original.png");
	public static final Image ICON_BOX_ON_TARGET = createImage("icons/box-on-target-original.png");
	public static final Image ICON_WALL = createImage("icons/wall-original.png");
	public static final Image ICON_TARGET = createImage("icons/target-original.png");
	public static final Image ICON_USER = createImage("icons/user-original.png");

	private final static Image createImage(String path) {
		return COMPONENT_FACTORY.createImageIcon(path).getImage();
	}
}
