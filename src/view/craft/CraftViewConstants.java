package view.craft;

import static view.Components.createImageIcon;

import javax.swing.ImageIcon;

public class CraftViewConstants {
	
	public static final double HEIGHT_TO_SCREENSIZE_RATIO = 1;
	public static final double WIDTH_TO_HEIGHT_RATIO = 1;
	public static final double GRIDBUTTON_RELATIVE_ICON_WIDTH = 0.5;
	public static final double GRIDBUTTON_RELATIVE_ICON_HEIGHT = 0.7;
	public static final String TITLE = "SOKOBAN - Craft your level";
	public static final String PANEL_GRID_TITLE = "Level grid";
	public static final String PANEL_OPTIONS_TITLE = "Edit level options";
	public static final String LABEL_WELCOME_TEXT = "Welcome! Click on a element to select it and then the cell's grid to mark them.";
	public static final String DIALOG_ERROR_TITLE = "ERROR";
	public static final String DIALOG_LEVEL_NOT_CORRECT_TITLE = "LEVEL NOT SAVED";
	public static final String DIALOG_LEVEL_NOT_CORRECT_TEXT = "Oops! One or more levels in the sequence seems to be incorrect!";
	public static final String DIALOG_IOERROR_TEXT = "An error occurred during input / output operation";
	public static final String BUTTON_SAVE_TEXT = "SAVE";
	public static final String BUTTON_LOAD_TEXT = "LOAD";
	public static final String BUTTON_RESET_TEXT = "RESET";
	public static final String BUTTON_BACK_TEXT = "BACK";
	public static final ImageIcon ICON_WALL = createImageIcon("icons/wall-original.png");
	public static final ImageIcon ICON_BOX = createImageIcon("icons/box-original.png");
	public static final ImageIcon ICON_TARGET = createImageIcon("icons/target-original.png");
	public static final ImageIcon ICON_USER = createImageIcon("icons/user-original.png");
	public static final ImageIcon ICON_SAVE = createImageIcon("icons/download.png");
	public static final ImageIcon ICON_LOAD = createImageIcon("icons/upload.png");
	public static final ImageIcon ICON_CANCEL = createImageIcon("icons/cross.png");
	public static final ImageIcon ICON_BACK = createImageIcon("icons/back.png");

	private CraftViewConstants() {}

}
