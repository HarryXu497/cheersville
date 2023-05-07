import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    // The maximum health of the object, which is also the default health
    public static final int DEFAULT_MAXIMUM_HEALTH = 100;
    // The default age for every game object
    public static final int DEFAULT_AGE = 0;

    // health of the object
    private double health;
    // age of the object
    private int age;

    /**
     * constructs a new game object with a default health
     */
    public GameObject() {
        this.health = DEFAULT_MAXIMUM_HEALTH;
        this.age = DEFAULT_AGE;
    }

    /**
     * constructs a new game object with a default health
     * @param health the custom health of the object
     * @throws IllegalArgumentException if the health is negative or larger than the maximum (100).
     */
    public GameObject(int health) {
        if (health < 0) {
            throw new IllegalArgumentException("health " + health + " is negative.");
        }
        if (health > DEFAULT_MAXIMUM_HEALTH) {
            throw new IllegalArgumentException("health " + health + " is larger than the maximum health " + DEFAULT_MAXIMUM_HEALTH + ".");
        }

        this.health = health;
    }

    /**
     * getHealth
     * gets the health of the game object
     * @return the health as an double
     */
    public double getHealth() {
        return this.health;
    }

    /**
     * setHealth
     * sets the health of the game object
     * @param health the new health of the object
     * @throws IllegalArgumentException if the health is negative or larger than the maximum (100).
     */
    public void setHealth(double health) {
        if (health < 0) {
            throw new IllegalArgumentException("health " + health + " is negative.");
        }
        if (health > DEFAULT_MAXIMUM_HEALTH) {
            throw new IllegalArgumentException("health " + health + " is larger than the maximum health " + DEFAULT_MAXIMUM_HEALTH + ".");
        }

        this.health = health;
    }

    /**
     * getAge
     * gets the age of the game object
     * @return the age as an integer
     */
    public int getAge() {
        return this.age;
    }

    /**
     * setAge
     * sets the age of the game object
     * @param age the new age of the object
     * @throws IllegalArgumentException if the age is negative
     */
    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("age " + age + " cannot be negative");
        }

        this.age = age;
    }

    /**
     * update
     * called to update the state of the game object (i.e. called once every loop).
     */
    public abstract void update();

    /**
     * draw
     * called to get the item to draw
     */
    public abstract Color draw();
}
