package view.initial;

import static view.Components.createImageIcon;

import javax.swing.ImageIcon;

public class InitialViewConstants {

	public static final double HEIGHT_TO_SCREENSIZE_RATIO = 0.9;
	public static final double WIDTH_TO_HEIGHT_RATIO = 1;
	public static final String DIALOG_ERROR_TITLE = "ERROR";
	public static final String DIALOG_IOERROR_TEXT = "An error occurred during an input/output operation";
	public static final String DIALOG_CLASS_NOT_FOUND_TITLE = "CLASS NOT FOUND";
	public static final String DIALOG_CLASS_NOT_FOUND_TEXT = "Loaded file is corrupted.";
	public static final String ICON_CRAFT = "icons/craft.png";
	public static final String TITLE = "SOKOBAN - InitialView";
	public static final String LABEL_WELCOME_TEXT = "Welcome to Sokoban! What would you like to do?";
	public static final String BUTTON_CRAFT_TEXT = "CRAFT A LEVEL";
	public static final String BUTTON_PLAY_TEXT = "PLAY";
	public static final String DIALOG_LEVEL_NOT_CORRECT_TITLE = "LEVEL NOT CORRECT";
	public static final String DIALOG_LEVEL_NOT_CORRECT_TEXT = "Oops! One or more levels in the sequence seems to be incorrect!";
	public static final String PANEL_LEVEL_SEQUENCE_TITLE = "Level sequence";
	public static final String PANEL_EDIT_LEVEL_SEQUENCE_TITLE = "Load levels, remove them or change their orders into the sequence";
	public static final String PANEL_SAVE_OR_LOAD_SEQUENCE_TITLE = "Save current sequence or load one";
	public static final String ICON_UP = "icons/arrow-up.png";
	public static final String ICON_DOWN = "icons/arrow-down.png";
	public static final String ICON_PLUS = "icons/plus-30px.png";
	public static final String ICON_MINUS = "icons/minus-30px.png";
	public static final String ICON_RESET = "icons/cross.png";
	public static final String ICON_DOWNLOAD = "icons/download.png";
	public static final String ICON_UPLOAD = "icons/upload.png";
	public static final ImageIcon ICON_PLAY = createImageIcon("icons/ok.png");

	private InitialViewConstants() {} // static class

}
