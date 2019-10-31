package view.craft;

/**
 * Contains constants used by classes of the view.craft package. It is a static
 * class, cannot be instantiated.
 */
public final class CraftConstants {

    /** The title of the generic error dialog. */
    public static final String DIALOG_ERROR_TITLE = "ERROR";

    /** The message of the level not correct error dialog. */
    public static final String DIALOG_LEVEL_NOT_CORRECT_TEXT = "An error occurred while trying to save the level.";

    /** The message of the level corrupted error dialog. */
    public static final String DIALOG_FILE_CORRUPTED_TEXT = "Loaded file is corrupted";

    /** The message of the sequence not correct error dialog. */
    public static final String DIALOG_SEQUENCE_NOT_CORRECT_TEXT = "Oops! One or more levels in the sequence seems to be incorrect!";

    /** The message of the input output error dialog. */
    public static final String DIALOG_IOERROR_TEXT = "An error occurred during input / output operation";

    /** The wall icon relative path. */
    public static final String ICON_WALL = "icons/wall.png";

    /** The box icon relative path. */
    public static final String ICON_BOX = "icons/box.png";

    /** The target icon relative path. */
    public static final String ICON_TARGET = "icons/target.png";

    /** The user icon relative path. */
    public static final String ICON_USER = "icons/user.png";

    /**
     * Cannot be instantiated.
     */
    private CraftConstants() {
    }
}
