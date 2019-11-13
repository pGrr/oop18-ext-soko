package view;

import java.awt.Image;
import java.util.Optional;

import javax.swing.ImageIcon;

import model.levelsequence.level.grid.element.Type;

/**
 * Associates each {@link Type} with an image.
 */
public enum TypeImage {

    /** The user. */
    USER(Type.USER, "icons/user.png"),

    /** The target. */
    TARGET(Type.TARGET, "icons/target.png"),

    /** The box. */
    BOX(Type.BOX, "icons/box.png"),

    /** The wall. */
    WALL(Type.WALL, "icons/wall.png"),

    /** The empty type, i.e. no image */
    EMPTY(Type.EMPTY, new String());

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
        this.image = path.isEmpty() ? null : new ImageIcon(ClassLoader.getSystemResource(path)).getImage();
    }


    /**
     * Returns an optional containing the image associated with the given type.
     * If type == Type.EMPTY returns an empty optional.
     *
     * @param type the type
     * @return the image associated with the given type, or an empty optional if Type.EMPTY
     */
    public static Optional<Image> getImageByType(final Type type) {
        for (TypeImage i : TypeImage.values()) {
            if (type.equals(i.type)) {
                return i.getImage() == null ? Optional.empty() : Optional.of(i.getImage());
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
