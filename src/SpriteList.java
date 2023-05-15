import java.awt.*;
import java.util.List;

/**
 * A list of sprites that allows for easy animation in game objects
 * @author Harry Xu
 * @version 1.0 - May 8th 2023
 */
public class SpriteList {
    /** The list of sprites to traverse through*/
    private final List<Image> sprites;

    /** determines the current sprite to return */
    private int index;

    /**
     * constructs a sprite list with a list of sprites
     * @param sprites the list of the sprites
     */
    public SpriteList(List<Image> sprites) {
        this.sprites = sprites;
        this.index = 0;
    }

    /**
     * nextImage
     * gets the next image in the list, looping around when the end is met
     * @return the current sprite
     */
    public Image nextImage() {
        Image currentImage = this.sprites.get(this.index);
        this.index = (this.index + 1) % this.sprites.size();
        return currentImage;
    }
}
