package view.game;

import java.awt.Image;
import javax.swing.ImageIcon;

import model.levelsequence.level.grid.element.Type;

/**
 * An enumeration associating each {@link Type} with it's image.
 */
public enum TypeImage {

    /** The user. */
    USER(Type.USER, "icons/user.png"),

    /** The target. */
    TARGET(Type.TARGET, "icons/target.png"),

    /** The box. */
    BOX(Type.BOX, "icons/box.png"),

    /** The wall. */
    WALL(Type.WALL, "icons/wall.png");

    private final Type type;
    private final Image image;

    /**
     * Instantiates a new type image.
     *
     * @param type the type
     * @param path the path of the image on the file-system
     */
    TypeImage(final Type type, final String path) {
        this.type = type;
        this.image = path.isEmpty() ? new ImageIcon().getImage()
                : new ImageIcon(ClassLoader.getSystemResource(path)).getImage();
    }

    /**
     * Gets the image associated with the given type.
     *
     * @param type the type
     * @return the image associated with the given type
     */
    public static Image getImageByType(final Type type) {
        for (TypeImage i : TypeImage.values()) {
            if (type.equals(i.type)) {
                return i.getImage();
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Gets the image.
     *
     * @return the image
     */
    public Image getImage() {
        return this.image;
    }
}
