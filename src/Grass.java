import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Grass extends GameObject {

    /**
     * A double between 0 and 1 that determines how often the grass reproduces
     */
    private static final double INDIVIDUAL_REPRODUCTION_THRESHOLD = 0.1;

    /**
     * A number between 0 and 1 that determines if the grass reproduces this frame
     */
    private static final double REPRODUCTION_THRESHOLD = 0.2;

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
     *
     * */
    public double getNutritionalValue() {
        return this.getHealth() / 5;
    }

    @Override
    public void update() {
        this.setAge(this.getAge() + 1);

        // A plant loses health proportional to their age
        double healthToLose = Math.pow(2, ((1.0 / 50.0) * this.getAge()));

        this.setHealth(Math.max(this.getHealth() - healthToLose, 0));
    }

    @Override
    public Image draw() {
        // TODO: fix this awful code
        try {
            return ImageIO.read(new File("grass.png"));
        } catch (IOException e) {
            return null;
        }
    }
}
