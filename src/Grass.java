import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * represents a grass game object
 * Additional features:
 *  - Sprites
 *      - currentSprite instance variable (line 29)
 *  - Non-constant nutritional value
 *      - getNutritionalValue method (line 76)
 *  - Health loss per iteration proportional to age
 *      - update method (line 85)
 * @author Harry Xu
 * @version 1.0 - May 8th 2023
 */
public class Grass extends GameObject {
    /**
     * A double between 0 and 1 that determines how often the grass reproduces
     */
    private static final double INDIVIDUAL_REPRODUCTION_THRESHOLD = 0.1;

    /**
     * A number between 0 and 1 that determines if the grass reproduces this frame
     */
    private static final double REPRODUCTION_THRESHOLD = 0.2;

    /** Sprite */
    private final Image currentSprite;

    /**
     * constructs a grass object with a random sprite
     */
    public Grass() {
        int y = (int) (Math.random() * SpriteSheet.GRASS_SPRITES.length);
        int x = (int) (Math.random() * SpriteSheet.GRASS_SPRITES[y].length);

        this.currentSprite = SpriteSheet.GRASS_SPRITES[y][x];
    }

    /**
     * reproduce
     * allows the grass to spread to adjacent tiles
     * @return a list of direction to put a new grass object
     * */
    public List<Direction> reproduce() {

        List<Direction> neighbors = new ArrayList<>();

        if (Math.random() < REPRODUCTION_THRESHOLD) {
            if (Math.random() < INDIVIDUAL_REPRODUCTION_THRESHOLD) {
                neighbors.add(Direction.UP);
            }

            if (Math.random() < INDIVIDUAL_REPRODUCTION_THRESHOLD) {
                neighbors.add(Direction.DOWN);
            }

            if (Math.random() < INDIVIDUAL_REPRODUCTION_THRESHOLD) {
                neighbors.add(Direction.LEFT);
            }

            if (Math.random() < INDIVIDUAL_REPRODUCTION_THRESHOLD) {
                neighbors.add(Direction.RIGHT);
            }
        }

        return neighbors;
    }

    /**
     * getNutritionalValue
     * gets the amount of health a plant restores based on its health
     * @return the nutritional value (i.e. how much health it replenishes)
     */
    public double getNutritionalValue() {
        return this.getHealth() / 5;
    }

    /**
     * update
     * called to update the state of the grass object
     */
    @Override
    public void update() {
        this.setAge(this.getAge() + 1);

        // A plant loses health proportional to their age
        double healthToLose = Math.pow(2, ((1.0 / 50.0) * this.getAge()));

        this.setHealth(Math.max(this.getHealth() - healthToLose, 0));
    }

    /**
     * draw
     * called to get the grass sprite to draw
     */
    @Override
    public Image draw() {
        return this.currentSprite;
    }
}
