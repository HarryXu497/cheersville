

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Person extends GameObject implements Movable, Collidable {
    /**
     * a double from 0 to 1 that determines at what percentage of the max health is the person considered hungry
     * This will create a change in behaviour from the person
     */
    private static final double HUNGRY_THRESHOLD = 0.5;

    /** The sex of the person, which determines if reproduction can occur*/
    private final Sex sex;

    /**
     * constructs a person with a sex and age
     * @param sex the biological sex of the person
     * */
    public Person(Sex sex) {
        super();

        this.sex = sex;
    }

    /**
     * constructs a person with a custom health, sex, and age
     * @param health the health of the person
     * @param sex the biological sex of the person
     * */
    public Person(int health, Sex sex) {
        super(health);

        this.sex = sex;
    }

    /**
     * isHungry
     * gets if the person is hungry, marking a change in behaviour
     * @return if the person is hungry.
     * */
    public boolean isHungry() {
        return this.getHealth() < HUNGRY_THRESHOLD * GameObject.DEFAULT_MAXIMUM_HEALTH;
    }

    @Override
    public void update() {
        this.setAge(this.getAge() + 1);

        // A person loses health proportional to their age
        double healthToLose = Math.pow(2, ((1.0 / 100.0) * this.getAge()));

        this.setHealth(Math.max(this.getHealth() - healthToLose, 0));
    }

    @Override
    public GameObject collide(GameObject other) {
        if (other instanceof Person) {
            Person p = (Person) other;
            if ((
                ((this.sex == Sex.MALE) && (p.sex == Sex.FEMALE)) ||
                ((this.sex == Sex.FEMALE) && (p.sex == Sex.MALE))) &&
                ((this.getAge() >= 18) && (p.getAge() >= 18)) &&
                ((!this.isHungry()) && (!p.isHungry()))
            ) {
                // Reproduce
                Sex newSex;

                if (Math.random() > 0.5) {
                    newSex = Sex.FEMALE;
                } else {
                    newSex = Sex.MALE;
                }

                return new Person(newSex);
            }
        }

        if (other instanceof Grass) {
            Grass g = (Grass) other;

            // replenishes an amount of health, maxing out at the maximum health
            this.setHealth(Math.min(this.getHealth() + g.getNutritionalValue(), GameObject.DEFAULT_MAXIMUM_HEALTH));

            g.setHealth(0);
        }

        return null;
    }

    @Override
    public Color draw() {
        Color color;

        if (this.getAge() < 18) {
            return Color.RED;
        }

        if (this.sex == Sex.FEMALE) {
            color = Color.YELLOW;
        } else {
            color = Color.BLUE;
        }

        return color;
    }

    /**
     * move
     * moves the player in a random direction in one of its 4 adjacent tiles
     */
    @Override
    public Direction move() {
        // Generate a random valid adjacent position
        int moveDirection = (int) (Math.random() * 4);

        // move up
        if (moveDirection == 0) {
            return Direction.UP;
        } else if (moveDirection == 1) {
            return Direction.DOWN;
        } else if(moveDirection == 2) {
            return Direction.LEFT;
        } else {
            return Direction.RIGHT;
        }
    }

    /**
     * getSex
     * returns the sex of this person
     * @return the sex of this person as a Sex enum
     */
    public Sex getSex() {
        return this.sex;
    }
}
