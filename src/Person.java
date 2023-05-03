

import java.awt.*;

public class Person extends GameObject implements Movable {

    private int age;
    private Sex sex;
    private Rectangle movableRegion;

    /**
     * constructs a person with a position, sex, and age
     * @param x the x position of the person
     * @param y the y position of the person
     * @param sex the biological sex of the person
     * @param age the age of the person
     * @param movableRegion the region in which the person can move
     * */
    public Person(int x, int y, Sex sex, int age, Rectangle movableRegion) {
        super(x, y);

        this.sex = sex;
        this.age = age;

        this.movableRegion = movableRegion;
    }

    /**
     * constructs a person with a position, custom health, sex, and age
     * @param x the x position of the person
     * @param y the y position of the person
     * @param health the health of the person
     * @param sex the biological sex of the person
     * @param age the age of the person
     * @param movableRegion the region in which the person can move
     * */
    public Person(int x, int y, int health, Sex sex, int age, Rectangle movableRegion) {
        super(x, y, health);

        this.sex = sex;
        this.age = age;

        this.movableRegion = movableRegion;
    }

    @Override
    public void update() {
        this.age++;
        this.setHealth(this.getHealth() - 1);
    }

    @Override
    public void collide(GameObject other) {
        if (other instanceof Person) {
            Person o = (Person) other;
            if (((this.sex == Sex.MALE) && (o.sex == Sex.FEMALE)) || ((this.sex == Sex.FEMALE) && (o.sex == Sex.MALE))) {
                // Reproduce
                System.out.println("Reproduction");
            }
        }
    }

    @Override
    public Color draw() {
        return this.sex == Sex.FEMALE ? Color.YELLOW : Color.BLUE;
    }

    @Override
    public void move() {

        int nextX = this.getX();
        int nextY = this.getY();

        // Generate a random valid adjacent position
        do {
            // 0: up
            // 1: down
            // 2: left
            // 3: right
            int moveDirection = (int) (Math.random() * 4);

            // move up
            if (moveDirection == 0) {
                nextY = this.getY() - 1;
            }

            // move down
            if (moveDirection == 1) {
                nextY = this.getY() + 1;
            }

            // move left
            if (moveDirection == 2) {
                nextX = this.getX() - 1;
            }

            // move right
            if (moveDirection == 3) {
                nextX = this.getX() + 1;
            }
        } while (!this.movableRegion.contains(nextX, nextY));

        this.setX(nextX);
        this.setY(nextY);
    }

    /**
     * getSex
     * returns the sex of this person
     * @return the sex of this person as a Sex enum
     */
    public Sex getSex() {
        return this.sex;
    }

    /**
     * getAge
     * returns the age of the person
     * @return the age of the person as an integer
     */
    public int getAge() {
        return this.age;
    }
}
