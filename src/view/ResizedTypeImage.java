package view;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import model.levelsequence.level.grid.element.Type;

/**
 * Associates each {@link Type} with the corresponding image provided by
 * {@link TypeImage}, all scaled to the given width and height. This is used in
 * order to not having to scale the same images again and again each time they
 * are needed, thus improving performance.
 */
public class ResizedTypeImage {

    private final Map<Type, Image> typeImageMap;

    /**
     * Creates an object which associates each {@link Type} with its corresponding
     * image, scaling all images with the given width and height.
     *
     * @param imageWidth  the image width
     * @param imageHeight the image height
     */
    public ResizedTypeImage(final int imageWidth, final int imageHeight) {
        this.typeImageMap = createResizedStandardImages(imageWidth, imageHeight);
    }

    /**
     * Returns an optional containing the scaled image for the given type or an
     * empty optional if type == Type.EMPTY.
     *
     * @param type the image type
     * @return an optional containing the scaled image for the given type or an
     *         empty optional if type == Type.EMPTY
     */
    public Optional<Image> get(final Type type) {
        return this.typeImageMap.get(type) == null ? Optional.empty() : Optional.of(this.typeImageMap.get(type));
    }

    /**
     * Creates the map associating each type with its associated scaled image.
     *
     * @param imageWidth  the image width
     * @param imageHeight the image height
     * @return the map
     */
    private Map<Type, Image> createResizedStandardImages(final int imageWidth, final int imageHeight) {
        Map<Type, Image> imageMap = new HashMap<>();
        for (Type type : Type.values()) {
            imageMap.put(type,
                    TypeImage.getImageByType(type).isPresent()
                            ? TypeImage.getImageByType(type).get().getScaledInstance(imageWidth, imageHeight,
                                    Image.SCALE_DEFAULT)
                            : null);
        }
        return imageMap;
    }
}
