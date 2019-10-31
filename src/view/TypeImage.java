package view;

import java.awt.Image;
import javax.swing.ImageIcon;

import model.element.Type;

// TODO: Auto-generated Javadoc
/**
 * The Enum TypeImage.
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

    /** The type. */
    private final Type type;

    /** The image. */
    private final Image image;

    /**
     * Instantiates a new type image.
     *
     * @param type the type
     * @param path the path
     */
    private TypeImage(Type type, String path) {
        this.type = type;
        this.image = path.isEmpty() ? new ImageIcon().getImage()
                : new ImageIcon(ClassLoader.getSystemResource(path)).getImage();
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

    /**
     * Gets the image by type.
     *
     * @param type the type
     * @return the image by type
     */
    public static Image getImageByType(Type type) {
        for (TypeImage i : TypeImage.values()) {
            if (type.equals(i.type)) {
                return i.getImage();
            }
        }
        throw new IllegalArgumentException();
    }
}
