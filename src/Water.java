import java.awt.*;


/**
 * represents a water game object, which acts as a barrier to other game objects
 * @author Harry Xu
 * @version 1.0 - May 13th 2023
 */
public class Water extends GameObject {

    /**
     * constructs a water game object with a random sprite
     */
    public Water() {
        this.setCurrentSprite(SpriteSheet.WATER_SPRITES[(int) (Math.random() * 3)]);
    }

    @Override
    public void update() {
        this.setAge(this.getAge() + 1);

        this.setCurrentSprite(SpriteSheet.WATER_SPRITES[(int) (Math.random() * 3)]);
    }
}
